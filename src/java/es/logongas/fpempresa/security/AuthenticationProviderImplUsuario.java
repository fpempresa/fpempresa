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

import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
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
    
    protected final Logger log = LogManager.getLogger(getClass());

    @Override
    public Principal authenticate(Credential credential, DataSession dataSession) throws BusinessException {
        
        if ((credential instanceof CredentialImplLoginPassword) == false) {
            return null;
        }
        CredentialImplLoginPassword credentialImplLoginPassword = (CredentialImplLoginPassword) credential;

        if ((credentialImplLoginPassword.getLogin() == null) || (credentialImplLoginPassword.getLogin().trim().isEmpty())) {
            return null;
        }

        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
        Usuario usuario = usuarioService.readOriginalByNaturalKey(dataSession, credentialImplLoginPassword.getLogin());

        if (usuario == null) {
            log.warn("Intento fallido de login. Usuario no existe : " + credentialImplLoginPassword.getLogin());
            throw new BusinessException("El correo no existe");
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
            
            throw new BusinessException("La cuenta está bloqueada hasta " + stringLockedUntil);
        }        
        
        
        
        
        if (!usuario.isValidadoEmail()) {
            throw new BusinessException("Tu cuenta no está validada. Si no has recibido un correo para validarla, ponte en contacto con el soporte de empleaFP.");
        }
           
        switch (usuario.getTipoUsuario()) {
            case ADMINISTRADOR:

                if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                    throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:" + usuario.getEstadoUsuario());
                }

                break;
            case CENTRO:

                if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getCentro() != null)) {
                    BusinessException businessException=new BusinessException("No puedes entrar ya que aun estás a la espera de ser aceptado o rechazado en el centro '" + usuario.getCentro() + "'");
                    businessException.getBusinessMessages().add(new BusinessMessage("Si eres la primera persona de tu centro que usa EmpleaFP , deberás ponerte en contacto con nosotros en 'soporte@empleafp.com' para que te aceptemos. "));
                    businessException.getBusinessMessages().add(new BusinessMessage("Si ya hay otros compañeros tuyos en EmpleaFP, deberías hablar con alguno de ellos para que te acepten en el centro. "));
                    
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
            log.warn("Intento fallido de login. Contraseña erronea: " + credentialImplLoginPassword.getLogin() + " " + usuario.getNumFailedLogins() + " " + usuario.getLockedUntil());
            usuarioService.updateFailedLogin(dataSession, usuario);
            throw new BusinessException("La contraseña no es válida");
        }



        usuarioService.updateSuccessfulLogin(dataSession, usuario);


        Principal principal = usuario;
        return principal;



    }
    
    @Override
    public Principal getPrincipalBySID(Serializable sid, DataSession dataSession) throws BusinessException {
        Integer idIdentity = (Integer) sid;
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);

        return usuarioService.readOriginal(dataSession, idIdentity);
    }

}
