/**
 * FPempresa Copyright (C) 2020 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */



package es.logongas.fpempresa.security;

import es.logongas.fpempresa.businessprocess.messageuid.PermitidoReenviarCorreoValidacion;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.util.EMailUtil;
import es.logongas.fpempresa.util.concurrent.EventCountInDay;
import es.logongas.fpempresa.util.validators.EMailValidator;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.security.authentication.impl.CredentialImplLoginPassword;
import es.logongas.ix3.security.authentication.AuthenticationProvider;
import es.logongas.ix3.security.authentication.Credential;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Autenticar a un usuario mediante el usuario y contraseña de moodle
 *
 * @author Lorenzo González
 */
public class AuthenticationProviderImplUsuario implements AuthenticationProvider {

    @Autowired
    CRUDServiceFactory crudServiceFactory;
    
    @Autowired
    Notification notification;
    
    protected final Logger log = LogManager.getLogger(getClass());

    private static final EventCountInDay eventCountInDayAuthenticate=new EventCountInDay(400);   
    
    @Override
    public Principal authenticate(Credential credential, DataSession dataSession) throws BusinessException {
        try {
            if ((credential instanceof CredentialImplLoginPassword) == false) {
                return null;
            }
            CredentialImplLoginPassword credentialImplLoginPassword = (CredentialImplLoginPassword) credential;

            if ((credentialImplLoginPassword.getLogin() == null) || (credentialImplLoginPassword.getLogin().trim().isEmpty())) {
                log.warn("El login no puede estar vacio");
                throw new BusinessException("El correo electrónico no puede estar vacío");
            }
            if ((credentialImplLoginPassword.getPassword()== null) || (credentialImplLoginPassword.getPassword().trim().isEmpty())) {
                log.warn("La contraseña no puede estar vacia para el usuario:" + EMailUtil.getAnonymizedEMail(credentialImplLoginPassword.getLogin()));
                throw new BusinessException("La contraseña no puede estar vacía");
            }

            if (EMailValidator.isValid(credentialImplLoginPassword.getLogin())==false) {
                log.warn("El usuario no es un correo electrónico válido : " + EMailUtil.getAnonymizedEMail(credentialImplLoginPassword.getLogin()));
                throw new BusinessException("El correo electrónico que acabas de usar no tiene un formato válido o simplemente no es un correo electrónico");
            }

            UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
            Usuario usuario = usuarioService.readOriginalByNaturalKey(dataSession, credentialImplLoginPassword.getLogin());

            if (usuario == null) {
                log.warn("Intento fallido de login. Usuario no existe : " + EMailUtil.getAnonymizedEMail(credentialImplLoginPassword.getLogin()));
                throw new BusinessException("El correo electrónico que acabas de indicar no existe");
            }

            if (usuarioService.isLocked(dataSession, usuario)==true) {
                Date dateLockedUntil=usuarioService.getLockedUntil(dataSession, usuario);

                String stringLockedUntil;
                if (DateUtils.isSameDay(dateLockedUntil, new Date())) {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("'las 'HH:mm:ss");
                    stringLockedUntil=simpleDateFormat.format(dateLockedUntil);
                } else {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("'el 'dd/MM/yyyy' a las 'HH:mm:ss");
                    stringLockedUntil=simpleDateFormat.format(dateLockedUntil);
                }

                log.warn("Intento fallido de login. La cuenta '" +  EMailUtil.getAnonymizedEMail(credentialImplLoginPassword.getLogin())+ "' ya está bloqueada hasta " + stringLockedUntil);
                throw new BusinessException("La cuenta está bloqueada hasta " + stringLockedUntil);
            }        




            if (usuario.isValidadoEmail()==false) {
                String msgEnvioadoCorreo;
                Date fechaUltimoEnvioCorreoValidacionEmail=usuario.getFechaUltimoEnvioCorreoValidacionEmail();
                if (fechaUltimoEnvioCorreoValidacionEmail==null) {
                    fechaUltimoEnvioCorreoValidacionEmail=usuario.getFecha();
                }
                
                if (DateUtils.isSameDay(fechaUltimoEnvioCorreoValidacionEmail, new Date())) {
                    msgEnvioadoCorreo="Hoy te hemos enviado un correo para validarla, comprueba que dicho correo no esté en tu carpeta de spam";
                } else {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                    String fechaUltimoEnvioCorreoValidacionEmailFormateado=simpleDateFormat.format(fechaUltimoEnvioCorreoValidacionEmail);
                    msgEnvioadoCorreo="El día " + fechaUltimoEnvioCorreoValidacionEmailFormateado + " te enviamos  un correo para validarla, comprueba que dicho correo no esté en tu carpeta de spam";
                }

                BusinessException businessException=new BusinessException("Tu dirección de correo aun no está validada.",PermitidoReenviarCorreoValidacion.class);
                businessException.getBusinessMessages().add(new BusinessMessage(msgEnvioadoCorreo));
                businessException.getBusinessMessages().add(new BusinessMessage("A veces el correo de validación tarda unos 30 minutos en llegar"));
                businessException.getBusinessMessages().add(new BusinessMessage("Si no está en tu carpeta de spam y ya han pasado 30 minutos, pulsa en el enlace de abajo para que te volvamos a enviar el correo de validación."));
                throw businessException;
            }

            switch (usuario.getTipoUsuario()) {
                case ADMINISTRADOR:

                    if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                        throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:" + usuario.getEstadoUsuario());
                    }

                    break;
                case CENTRO:

                    if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getCentro() != null)) {

                        BusinessException businessException=new BusinessException("No puedes entrar ya que estás a la espera de ser aceptado en el '" + usuario.getCentro() + "'");
                        int numUsuariosCentro=usuarioService.numUsuariosCentro(dataSession, usuario.getCentro());
                        if (numUsuariosCentro==0) {
                            businessException.getBusinessMessages().add(new BusinessMessage("Como eres la primera persona de tu centro que usa EmpleaFP , debes ponerte en contacto con nosotros en '" + Config.getSetting("app.correoSoporte") + "' para que te aceptemos en el '" + usuario.getCentro() + "'"));
                        } else if (numUsuariosCentro==1) {
                            businessException.getBusinessMessages().add(new BusinessMessage("Como ya hay un compañero de tu centro usando EmpleaFP, deberías hablar con él para que te acepte en el '" + usuario.getCentro() + "'"));
                        } else if (numUsuariosCentro>1) {
                            businessException.getBusinessMessages().add(new BusinessMessage("Como ya hay " + numUsuariosCentro + " compañeros de tu centro usando EmpleaFP, deberías hablar con alguno de ellos para que te acepte en el '" + usuario.getCentro() + "'"));
                        } else {
                            throw new RuntimeException("Error de lógica:" + numUsuariosCentro + " " + usuario.getCentro());
                        }


                        throw businessException;
                    }

                    if ((usuario.getCentro() != null) && (usuario.getCentro().getEstadoCentro() != EstadoCentro.PERTENECE_A_FPEMPRESA)) {
                        throw new BusinessException("Tu centro debe pertenecer a FPempresa para poder entrar");
                    }

                    break;
                case EMPRESA:

                    if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getEmpresa() != null)) {
                        throw new BusinessException("No puedes entrar ya que aun estás a la espera de ser aceptado en la empresa '" + usuario.getEmpresa() + "'");
                    }

                    break;
                case TITULADO:

                    if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                        throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:" + usuario.getEstadoUsuario());
                    }

                    break;
            }


            String plainPassword = credentialImplLoginPassword.getPassword();
            if (usuarioService.checkPassword(dataSession, usuario, plainPassword)==false) {
                log.warn("Intento fallido de login. Contraseña erronea: " + EMailUtil.getAnonymizedEMail(credentialImplLoginPassword.getLogin())+ " " + usuario.getNumFailedLogins() + " " + usuario.getLockedUntil());
                usuarioService.updateFailedLogin(dataSession, usuario);
                throw new BusinessException("La contraseña no es válida");
            }


            if (usuario.getNumFailedLogins()>0) {
                log.warn("Login exitoso despues de " + usuario.getNumFailedLogins() + " intentos fallidos de " + EMailUtil.getAnonymizedEMail(credentialImplLoginPassword.getLogin()));
            }

            usuarioService.updateSuccessfulLogin(dataSession, usuario);


            Principal principal = usuario;
            return principal;


        } catch (BusinessException businessException) {
            if (eventCountInDayAuthenticate.isSafe(new EventCountInDayNotifierImplAuthenticate(notification) )) {
                throw businessException;
            } else {
                throw new BusinessException("No ha sido posible autenticar el usuario");
            }
        }
    }
    
    @Override
    public Principal getPrincipalBySID(Serializable sid, DataSession dataSession) throws BusinessException {
        Integer idIdentity = (Integer) sid;
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);

        return usuarioService.readOriginal(dataSession, idIdentity);
    }

    private class EventCountInDayNotifierImplAuthenticate implements EventCountInDay.Notifier {
        
        Notification notification;

        public EventCountInDayNotifierImplAuthenticate(Notification notification) {
            this.notification = notification;
        }

        @Override
        public void notify(int threshold,int currentValue) {
            notification.mensajeToAdministrador("Alcanzado límite de falloss en BusinessExceptions en Authenticate de usuario."+currentValue, "CurrentValue="+currentValue+"\n"+"threshold="+threshold);
        }
    }
}
