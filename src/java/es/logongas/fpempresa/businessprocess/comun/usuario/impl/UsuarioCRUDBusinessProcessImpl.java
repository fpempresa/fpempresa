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

package es.logongas.fpempresa.businessprocess.comun.usuario.impl;

import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.captcha.CaptchaService;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.fpempresa.util.ImageUtil;
import es.logongas.fpempresa.util.concurrent.EventCountInDay;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.rule.ActionRule;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleContext;
import es.logongas.ix3.rule.RuleGroupPredefined;
import es.logongas.ix3.security.authorization.BusinessSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Usuario, Integer> implements UsuarioCRUDBusinessProcess {

    static final Logger log = LogManager.getLogger(UsuarioCRUDBusinessProcessImpl.class);    
    
    @Autowired
    Notification notification;
 
    @Autowired    
    CaptchaService captchaService;   

    private static final EventCountInDay eventCountInDayInsert=new EventCountInDay(300);    
    private static final EventCountInDay eventCountInDayEnviarMailValidarEMail=new EventCountInDay(100);    
    
    
    @Override
    public Usuario update(UpdateArguments<Usuario> updateArguments) throws BusinessException {
        Usuario principal = (Usuario) updateArguments.principal;
        Usuario usuario = updateArguments.entity;
        Usuario usuarioOriginal=updateArguments.originalEntity;
       
        if (principal.getTipoUsuario()!=TipoUsuario.ADMINISTRADOR) {
            usuario.setValidadoEmail(usuarioOriginal.isValidadoEmail());
        }

        return super.update(updateArguments); 
    }

    @Override
    public Usuario insert(InsertArguments<Usuario> insertArguments) throws BusinessException {
        try {
            UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
            Usuario usuario = insertArguments.entity;


            String word=usuario.getCaptchaWord();
            String keyCaptcha=usuario.getKeyCaptcha();


            //Verificar el captcha
            if (isRequiredCaptcha(usuario, insertArguments.principal)) {
                try {
                    if (captchaService.solveChallenge(insertArguments.dataSession,keyCaptcha, word)==false) {
                        throw new BusinessException("El texto de la imagen no es correcto");
                    }
                } catch (Exception ex) {
                    throw new BusinessException("El texto de la imagen no es correcto");
                }
            }



            Usuario usuarioPrevio=usuarioCRUDService.readOriginalByNaturalKey(insertArguments.dataSession, usuario.getEmail());
            if (usuarioPrevio!=null) {
                throw new BusinessException("Ya existe un usuario con el correo: '"+ usuario.getEmail()+"'");
            }

            Usuario newUsuario=super.insert(insertArguments); 

            return newUsuario;
        } catch (BusinessException businessException) {
            if (eventCountInDayInsert.isSafe(new EventCountInDayNotifierImplInsert(notification))) {
                throw businessException;
            } else {
                throw new BusinessException("No ha sido posible insertar el usuario");
            }
        }
    }

    @Override
    public Usuario create(CreateArguments createArguments) throws BusinessException {
        
        Usuario usuario=super.create(createArguments); 
        
        return usuario;
    }

    
    
    
    @Override
    public void updatePassword(UpdatePasswordArguments updatePasswordArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        Usuario principal = (Usuario) updatePasswordArguments.principal;

        if (updatePasswordArguments.usuario == null) {
            throw new BusinessException("No hay usuario al que cambiar la contraseña");
        } else if (principal == null) {
            throw new BusinessException("Debes haber entrado para cambiar la contraseña");
        } else if (updatePasswordArguments.usuario.getIdIdentity() == principal.getIdIdentity()) {
            if (usuarioCRUDService.checkPassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.currentPassword)) {
                usuarioCRUDService.updatePassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.newPassword);
            } else {
                throw new BusinessException("La contraseña actual no es válida");
            }
        } else if (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            //Si eres administrador no necesitas la contraseña actual
            usuarioCRUDService.updatePassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.newPassword);
        } else {
            throw new BusinessException("Solo un Administrador o el propio usuario puede cambiar la contraseña");
        }

    }

    @Override
    public Usuario getUsuarioFromTitulado(GetUsuarioFromTituladoArguments getUsuarioFromTituladoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        return usuarioCRUDService.getUsuarioFromTitulado(getUsuarioFromTituladoArguments.dataSession, getUsuarioFromTituladoArguments.titulado.getIdTitulado());
    }

    @Override
    public byte[] getFoto(GetFotoArguments getFotoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        return usuarioCRUDService.read(getFotoArguments.dataSession, getFotoArguments.usuario.getIdIdentity()).getFoto();
    }

    @Override
    public void updateFoto(UpdateFotoArguments updateFotoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        //Comprobar si la foto se puede convertir en Imagen. Se hace pq luego sino falla JarperReports ya que no se ve la imagen
        if (ImageUtil.isValid(updateFotoArguments.foto)==false) {
            throw new BusinessException("El formato de la imagen no es válida");           
        }

        updateFotoArguments.usuario.setFoto(updateFotoArguments.foto);
        usuarioCRUDService.update(updateFotoArguments.dataSession, updateFotoArguments.usuario);

    }

    @Override
    public Usuario updateEstadoUsuario(UpdateEstadoUsuarioArguments updateEstadoUsuarioArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        updateEstadoUsuarioArguments.usuario.setEstadoUsuario(updateEstadoUsuarioArguments.estadoUsuario);
        return usuarioCRUDService.update(updateEstadoUsuarioArguments.dataSession, updateEstadoUsuarioArguments.usuario);
    }

    @Override
    public Usuario updateCentro(UpdateCentroArguments updateCentroArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        updateCentroArguments.usuario.setCentro(updateCentroArguments.centro);
        
        String emailUsuario=updateCentroArguments.usuario.getEmail();
        Centro centro=updateCentroArguments.centro;
        String emailContactoCentro=null;
        if (centro!=null) {
            Contacto contactoCentro=updateCentroArguments.centro.getContacto();
            if (contactoCentro!=null) {
                emailContactoCentro=contactoCentro.getEmail();
            }
        }
        
        if ((emailUsuario!=null) && (emailUsuario.trim().isEmpty()==false) && (emailContactoCentro!=null) && (emailContactoCentro.trim().isEmpty()==false)) {
            if (emailUsuario.equals(emailContactoCentro)) {
                //Si el nuevo usuario es el del correo del centro seguro que está aceptado.
                updateCentroArguments.usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
            } else {
                updateCentroArguments.usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
            }
        } else {
            updateCentroArguments.usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
        }


        return usuarioCRUDService.update(updateCentroArguments.dataSession, updateCentroArguments.usuario);
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertCentro'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertCentro(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        if ((usuario.getTipoUsuario() == TipoUsuario.CENTRO)) {

            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido para usuarios de centro");
            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para usuarios de centro");

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getCentro() != null, "Ya debes pertenecer a un centro");

                businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
                businessTrue(principal.getCentro().getIdCentro() == usuario.getCentro().getIdCentro(), "El centro debe ser el mismo centro que el tuyo");
            } else if (principal == null) {
                businessTrue(usuario.getEstadoUsuario() == null, "El estado debe ser vacio");
                businessTrue(usuario.getCentro() == null, "El centro debe ser vacio");
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un profesor");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateCentroAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateCentroAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.CENTRO)) {

            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido para usuarios de centro");
            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para usuarios de centro");

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getCentro() != null, "Ya debes pertenecer a un centro");

                if (principal.getIdIdentity() == usuario.getIdIdentity()) {
                    businessTrue(usuario.getCentro() != null, "El centro es requerido");
                    businessTrue(usuarioOriginal.getCentro() != null, "El usuario ya debía de tener un centro");

                    boolean ahoraTieneCentro = (usuario.getCentro() != null);
                    boolean antesTeniaCentro = (usuarioOriginal.getCentro() != null);

                    if (ahoraTieneCentro && antesTeniaCentro) {
                        if (usuario.getCentro().getIdCentro() != usuarioOriginal.getCentro().getIdCentro()) {
                            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de centro");
                        }
                    } else if (ahoraTieneCentro && !antesTeniaCentro) {
                        businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de centro");
                    } else if (!ahoraTieneCentro && antesTeniaCentro) {
                        throw new BusinessException("No puedes estar sin centro");
                    } else if (!ahoraTieneCentro && !antesTeniaCentro) {
                        throw new BusinessException("No puedes estar sin centro");
                    } else {
                        throw new RuntimeException("Error de logica:" + ahoraTieneCentro + "," + antesTeniaCentro);

                    }

                } else {
                    businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
                    businessTrue(principal.getCentro().getIdCentro() == usuario.getCentro().getIdCentro(), "El centro debe ser el mismo centro que el tuyo");
                }
            } else {
                throw new BusinessSecurityException("No tienes permiso para actualizar un profesor");
            }
        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertEmpresa'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.EMPRESA)) {

            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido para usuarios de empresa");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para usuarios de empresa");

            //El principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para usuarios de una empresa");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getEmpresa() != null, "Ya debes pertenecer a una empresa");

                businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para usuarios de una empresa");
                businessTrue(usuario.getEmpresa().getIdEmpresa() == principal.getEmpresa().getIdEmpresa(), "La empresa debe ser la misma empresa que la tuya");
            } else if (principal == null) {
                businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida al insertar el usuario");
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un usuario de una empresa");
            }
        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateEmpresa'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.EMPRESA)) {

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getEmpresa() != null, "Ya debes pertenecer a una empresa");

                if (principal.getIdIdentity() == usuario.getIdIdentity()) {
                    businessTrue(usuario.getEmpresa() != null, "La empresa es requerida");
                    businessTrue(usuarioOriginal.getEmpresa() != null, "El usuario ya debía de tener una empresa");

                    boolean ahoraTieneEmpresa = (usuario.getEmpresa() != null);
                    boolean antesTeniaEmpresa = (usuarioOriginal.getEmpresa() != null);

                    if (ahoraTieneEmpresa && antesTeniaEmpresa) {
                        if (usuario.getEmpresa().getIdEmpresa() != usuarioOriginal.getEmpresa().getIdEmpresa()) {
                            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de empresa");
                        }
                    } else if (ahoraTieneEmpresa && !antesTeniaEmpresa) {
                        businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El empresario debe estar pendiente de aceptación al cambiar de empresa");
                    } else if (!ahoraTieneEmpresa && antesTeniaEmpresa) {
                        throw new BusinessException("No puedes estar sin empresa");
                    } else if (!ahoraTieneEmpresa && !antesTeniaEmpresa) {
                        throw new BusinessException("No puedes estar sin empresa");
                    } else {
                        throw new RuntimeException("Error de logica:" + ahoraTieneEmpresa + "," + antesTeniaEmpresa);

                    }
                } else {
                    businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                    businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para el usuario");
                    businessTrue(principal.getEmpresa().getIdEmpresa() == usuario.getEmpresa().getIdEmpresa(), "La empresa debe ser de la misma empresa que la tuya");
                }
            } else {
                throw new BusinessSecurityException("No tienes permiso para actualizar un profesor");
            }

        }

        return true;

    }

    @ActionRule(groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private void establecerEstadoEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.EMPRESA)) {
            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        }
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertTitulado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertTitulado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.TITULADO)) {

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para titulados");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para titulados");
            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido al insertar el usuario");

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if (principal == null) {
                //No hace falta comprobar nada ya que no hay usuario pero se permite insertat sin principal
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un titulado");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateTituladoAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateTituladoAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.TITULADO)) {

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para titulados");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para titulados");

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.TITULADO)) {
                securityTrue(principal.getIdIdentity() == usuario.getIdIdentity(), "No puedes actualizar a un titulado que no sea tu mismo");
            } else {
                throw new BusinessSecurityException("Solo el titulado o los administradores pueden actualizar al titulado");
            }

            if ((usuario.getTitulado() != null) && (usuarioOriginal.getTitulado() != null)) {
                businessTrue(usuario.getTitulado().getIdTitulado() == usuarioOriginal.getTitulado().getIdTitulado(), "No es posible cambiar de titulado");
            } else if ((usuario.getTitulado() == null) && (usuarioOriginal.getTitulado() != null)) {
                throw new BusinessException("No es posible quitar el titulado");
            } else if ((usuario.getTitulado() != null) && (usuarioOriginal.getTitulado() == null)) {
                //No pasa nada se le ha puesto el titulado
            } else if ((usuario.getTitulado() == null) && (usuarioOriginal.getTitulado() == null)) {
                //No pasa nada sigue sin titulado
            } else {
                throw new BusinessException("Error de lógica:" + (usuario.getTitulado()) + "," + (usuarioOriginal.getTitulado()));
            }

        }

        return true;

    }

    @ActionRule(groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private void establecerEstadoTitulado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.TITULADO)) {
            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        }
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertAdministradorAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertAdministradorAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else {
                throw new BusinessSecurityException("Solo los administradores pueden insertar otro administrador");
            }

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para los administradores");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para los administradores");
            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido los administradores");
            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Los administradores siempre deben estar aceptados");

        }

        return true;

    }
    
    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateAdministradorAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateAdministradorAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else {
                throw new BusinessSecurityException("Solo los administradores pueden actualizar otro administrador");
            }

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para los administradores");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para los administradores");
            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido los administradores");
            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Los administradores siempre deben estar aceptados");

        }

        return true;

    }

    private void businessTrue(boolean valid, String message) throws BusinessException {
        if (valid == false) {
            throw new BusinessException(message);
        }
    }

    private void securityTrue(boolean valid, String message) throws BusinessException {
        if (valid == false) {
            throw new BusinessSecurityException(message);
        }
    }

    @Override
    public void enviarMailResetearContrasenya(EnviarMailResetearContrasenyaArguments enviarMailResetearContrasenyaArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        usuarioCRUDService.enviarMailResetearContrasenya(enviarMailResetearContrasenyaArguments.dataSession, enviarMailResetearContrasenyaArguments.email);
    }

    @Override
    public void enviarMailValidarEMail(EnviarMailValidarEMailArguments enviarMailValidarEMailArguments) throws BusinessException {
      
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);        

        DataSession dataSession=enviarMailValidarEMailArguments.dataSession;
        String email=enviarMailValidarEMailArguments.email;
        String word=enviarMailValidarEMailArguments.captchaWord;
        String keyCaptcha=enviarMailValidarEMailArguments.keyCaptcha; 


        if ((email==null) || email.trim().isEmpty()) {
            throw new BusinessException("La dirección de correo no puede estar vacía");
        }

        if (captchaService.solveChallenge(dataSession,keyCaptcha, word)==false) {
            throw new BusinessException("El texto de la imagen no es correcto");
        }

        try { 
            Usuario usuario=usuarioCRUDService.readByNaturalKey(dataSession, email);
            
            fireConstraintRule_DebeExistirElUsuario(dataSession,usuario,email);
            fireConstraintRule_ElUsuarioNoPuedeEstarValidado(dataSession, usuario, email);
            fireConstraintRule_AlcanzadoLimiteEnvioCorreos(dataSession,usuario);
            fireConstraintRule_DemasiaodProntoVolverEnviarCorreo(dataSession,usuario);

            
            usuarioCRUDService.enviarMailValidarEMail(dataSession, usuario);
        } catch (BusinessException businessException) {
            if (eventCountInDayEnviarMailValidarEMail.isSafe(new EventCountInDayNotifierImplEnviarMailValidarEMail(notification))) {
                throw businessException;
            }
        } 
        
    }    

    
    @Override
    public void resetearContrasenya(ResetearContrasenyaArguments resetearContrasenyaArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        usuarioCRUDService.resetearContrasenya(resetearContrasenyaArguments.dataSession,resetearContrasenyaArguments.usuario, resetearContrasenyaArguments.claveResetearContrasenya, resetearContrasenyaArguments.nuevaContrasenya);
    }

    @Override
    public byte[] getCurriculum(GetCurriculumArguments getCurriculumArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        
        return usuarioCRUDService.getCurriculum(getCurriculumArguments.dataSession, getCurriculumArguments.usuario);
    }
    
    @Override
    public void enviarMensajeSoporte(EnviarMensajeSoporteArguments enviarMensajeSoporteArguments) throws BusinessException {
        
        throw new BusinessException("Esta funcionalidad está deshabilitada");
        
        //notification.mensajeSoporte(enviarMensajeSoporteArguments.nombre, enviarMensajeSoporteArguments.correo, enviarMensajeSoporteArguments.mensaje);
    }    
    
    @Override
    public void notificarUsuarioInactivo(NotificarUsuarioInactivoArguments notificarUsuarioInactivoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        usuarioCRUDService.notificarUsuarioInactivo(notificarUsuarioInactivoArguments.dataSession, notificarUsuarioInactivoArguments.usuario);
    }
    
    @Override
    public void softDelete(SoftDeleteArguments softDeleteArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        usuarioCRUDService.softDelete(softDeleteArguments.dataSession, softDeleteArguments.usuario); 
    }

    @Override
    public void validarEmail(ValidarEmailArguments validarEmailArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        usuarioCRUDService.validarEmail(validarEmailArguments.dataSession,validarEmailArguments.usuario, validarEmailArguments.claveValidarEmail);
    } 
    
    @Override
    public void cancelarSuscripcion(CancelarSuscripcionArguments cancelarSuscripcionArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        usuarioCRUDService.cancelarSuscripcion(cancelarSuscripcionArguments.dataSession,cancelarSuscripcionArguments.usuario, cancelarSuscripcionArguments.publicToken);
    } 
    
    
    private boolean isRequiredCaptcha(Usuario usuario,Principal principal) {
        boolean required;
        
        if ((usuario==null) || (principal==null)) {
            required=true;
        } else {
            if (principal instanceof Usuario) {
                Usuario usuarioPrincipal=(Usuario)principal;

                if ((usuario.getTipoUsuario()==TipoUsuario.EMPRESA) && (usuarioPrincipal.getTipoUsuario()==TipoUsuario.EMPRESA)) {
                    required=false;
                } else if (usuarioPrincipal.getTipoUsuario()==TipoUsuario.ADMINISTRADOR) {
                    required=false;
                } else {
                    required=true;
                }
            } else {
                required=true;
            }
        }
        
        return required;
        
    }

    /*************************************************************/
    /****************** Reglas de restricciones ******************/
    /*** BEGIN ***/

    private void fireConstraintRule_DebeExistirElUsuario(DataSession dataSession, Usuario usuario,String email) throws BusinessException {
        if (usuario==null) {
            throw new BusinessException("No existe ningún usuario con el correo '"+email+"'. Comprueba que esté bien escrito el correo. Si el correo está bien escrito, vuelve a darte de alta en EmpleaFP ya que lo normal es que no lo escribieras correctamente al darte de alta.");
        }        
    }    
    private void fireConstraintRule_ElUsuarioNoPuedeEstarValidado(DataSession dataSession, Usuario usuario,String email) throws BusinessException {
        if (usuario.isValidadoEmail()==true) {
            throw new BusinessException("Ya está validado el correo '"+email+"'. Por lo tanto no es necesario que te enviemos el correo de validación.");
        }        
    } 
    
    private void fireConstraintRule_AlcanzadoLimiteEnvioCorreos(DataSession dataSession, Usuario usuario) throws BusinessException {
        final int LIMITE_ENVIO_CORREOS=5;
        
        
        if (usuario.getNumEnviosCorreoValidacionEmail()>=LIMITE_ENVIO_CORREOS) {
            BusinessException businessException=new BusinessException("No es posible volver a enviar el correo de validación puesto que ya te lo hemos enviado " + usuario.getNumEnviosCorreoValidacionEmail() + " veces.");
            businessException.getBusinessMessages().add(new BusinessMessage("Debes escribir un correo a '" + Config.getSetting("app.correoSoporte") + "' solicitando que validemos tu correo."));
            throw businessException;
        }
        
    }
    private void fireConstraintRule_DemasiaodProntoVolverEnviarCorreo(DataSession dataSession, Usuario usuario) throws BusinessException {
        final int MINUTOS_ESPERAR_NUEVO_ENVIO_CORREO=30;
        
        Date fechaUltimoEnvioCorreoValidacionEMail=usuario.getFechaUltimoEnvioCorreoValidacionEmail();
        if (fechaUltimoEnvioCorreoValidacionEMail==null) {
            //Si nunca se ha enviado el correo seguro que no falla la validación
            return;
        }
        Date fechaProximoEnvioCorreoValidacionEMail=DateUtil.add(fechaUltimoEnvioCorreoValidacionEMail, DateUtil.Interval.MINUTE, MINUTOS_ESPERAR_NUEVO_ENVIO_CORREO);
        String horaUltimoEnvioCorreoValidacionEMailFormateada=new SimpleDateFormat("HH:mm").format(fechaUltimoEnvioCorreoValidacionEMail);
        String horaProximoEnvioCorreoValidacionEMailFormateada=new SimpleDateFormat("HH:mm").format(fechaProximoEnvioCorreoValidacionEMail);


        Date ahora=new Date();
        if (ahora.before(fechaProximoEnvioCorreoValidacionEMail)) {
            BusinessException businessException=new BusinessException("Aun no te podemos volver enviar el correo de validación puesto que ya te lo hemos enviado a las " + horaUltimoEnvioCorreoValidacionEMailFormateada);
            businessException.getBusinessMessages().add(new BusinessMessage("Espera hasta las " + horaProximoEnvioCorreoValidacionEMailFormateada + " para volver a pedir que te lo enviemos"));
            throw businessException;            
        }
        
        
    }

    /*********************************************************************/
    /****************** Notificaciones de EventCountDay ******************/
    /*** BEGIN ***/

    private class EventCountInDayNotifierImplInsert implements EventCountInDay.Notifier {
        
        Notification notification;

        public EventCountInDayNotifierImplInsert(Notification notification) {
            this.notification = notification;
        }

        @Override
        public void notify(int threshold,int currentValue) {
            notification.mensajeToAdministrador("Alcanzado límite de fallos en BusinessExceptions en inserciones de usuario."+currentValue, "CurrentValue="+currentValue+"\n"+"threshold="+threshold);
        }
    }
    
    
    private class EventCountInDayNotifierImplEnviarMailValidarEMail implements EventCountInDay.Notifier {
        
        Notification notification;

        public EventCountInDayNotifierImplEnviarMailValidarEMail(Notification notification) {
            this.notification = notification;
        }

        @Override
        public void notify(int threshold,int currentValue) {
            notification.mensajeToAdministrador("Alcanzado límite de fallos en BusinessExceptions en Enviar Mail para Validar EMail de usuario."+currentValue, "CurrentValue="+currentValue+"\n"+"threshold="+threshold);
        }
    }    
}
