/**
 * FPempresa Copyright (C) 2014 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.modelo.comun.usuario;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.security.model.User;
import es.logongas.ix3.core.annotations.Label;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.security.authorization.BusinessSecurityException;
import es.logongas.ix3.rule.ActionRule;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleContext;
import es.logongas.ix3.rule.RuleGroupPredefined;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Usuario extends User implements Principal {

    @Email
    @NotBlank
    @Label("Correo electrónico")
    private String email;

    @NotBlank
    private String nombre;

    @Label("Apellidos")
    @NotBlank
    private String apellidos;

    private byte[] foto;

    @Label("Contraseña")
    private String password;

    @NotNull
    @Label("Tipo de usuario")
    private TipoUsuario tipoUsuario;

    private Centro centro;

    private Titulado titulado;

    private Empresa empresa;

    @Label("Estado del usuario")
    private EstadoUsuario estadoUsuario;

    private boolean validadoEmail;

    private String claveValidacionEmail;

    private Date fecha;

    public Usuario() {
        this.tipoUsuario = TipoUsuario.TITULADO;
    }

    @Override
    public String toString() {
        return name;
    }

    @ConstraintRule(disabled = true, message = "No está habilitado el registro de usuarios", groups = RuleGroupPredefined.PreInsert.class, priority = -20)
    private boolean isProhibidoNuevoUsuario() {
        return true;
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertCentroEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertCentroAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        if ((this.getTipoUsuario() == TipoUsuario.CENTRO)) {

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getCentro() != null, "Ya debes pertenecer a un centro");
            } else if (principal == null) {
                //No hace falta comprobar nada ya que no hay usuario
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un profesor");
            }

            businessTrue(this.titulado == null, "El titulado está prohibido para usuarios de centro");
            businessTrue(this.empresa == null, "La empresa está prohibida para usuarios de centro");

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                businessTrue(this.centro != null, "El centro es requerido para usuarios de centro");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                businessTrue(this.centro != null, "El centro es requerido para los profesores");
                businessTrue(this.getCentro().getIdCentro() == principal.getCentro().getIdCentro(), "El centro debe ser del mismo centro que el tuyo");
            } else {
                businessTrue(this.centro == null, "El centro está prohibido al insertar el usuario");
            }

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El profesor debe estar aceptado al insertarlo el administrador");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El profesor debe estar aceptado al insertarlo otro profesor");
            } else {
                businessTrue(this.estadoUsuario == null, "Es estado debe estar vacio al no haber centro");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateCentroAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckUpdateCentroAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((this.getTipoUsuario() == TipoUsuario.CENTRO)) {

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO) && (principal.getIdIdentity() == this.getIdIdentity())) {
                //Somos nosotros actualizando el usuario, no hacemos nada
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getCentro() != null, "Ya debes pertenecer a un centro");
            } else {
                throw new BusinessSecurityException("No tienes permiso para actualizar un profesor");
            }

            businessTrue(this.titulado == null, "El titulado está prohibido para usuarios de centro");
            businessTrue(this.empresa == null, "La empresa está prohibida para usuarios de centro");

            if ((this.getCentro() != null) && (usuarioOriginal.getCentro() != null)) {
                if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                    businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El profesor debe estar aceptado al actualizarlo el administrador");
                } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO) && (principal.getIdIdentity() == this.getIdIdentity())) {
                    
                    if (this.getCentro().getIdCentro() != usuarioOriginal.getCentro().getIdCentro()) {
                        businessTrue(this.estadoUsuario == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de centro");
                    }
                    
                } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                    businessTrue(usuarioOriginal.getCentro().getIdCentro() == principal.getCentro().getIdCentro(), "El usuario debe permanecer a tu centro");
                    businessTrue(this.getCentro().getIdCentro() == usuarioOriginal.getCentro().getIdCentro(), "No puedes cambiar el centro del profesor");
                } else {
                    throw new RuntimeException("No puedes actualizar el usuario , pero ya se había comprobado asi que esto es un error");
                }

                businessTrue(this.getCentro().getIdCentro() != usuarioOriginal.getCentro().getIdCentro(), "No es posible cambiar de Centro");
             
            } else if ((this.getCentro() == null) && (usuarioOriginal.getCentro() != null)) {
                throw new BusinessException("No es posible quitar el Centro");
            } else if ((this.getCentro() != null) && (usuarioOriginal.getCentro() == null)) {
                
                if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                    businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El profesor debe estar aceptado al actualizarlo el administrador");
                } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO) && (principal.getIdIdentity() == this.getIdIdentity())) {
                    businessTrue(this.estadoUsuario == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar aceptado al actualizarlo el administrador");
                } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                    businessTrue(this.getCentro().getIdCentro() == principal.getCentro().getIdCentro(), "El centro del usuario debe ser el mismo que el tuyo");
                    businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El profesor debe estar aceptado al actualizarlo otro profesor");
                } else {
                    throw new RuntimeException("No puedes actualizar el usuario , pero ya se había comprobado asi que esto es un error");
                }
                    
            } else if ((this.getCentro() == null) && (usuarioOriginal.getCentro() == null)) {
                //No pasa nada sigue sin Centro
            } else {
                throw new BusinessException("Error de lógica:" + (this.getCentro()) + "," + (usuarioOriginal.getCentro()));
            }



        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertEmpresaAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertEmpresaAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        if ((this.getTipoUsuario() == TipoUsuario.EMPRESA)) {

            //El principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getEmpresa() != null, "Ya debes pertenecer a una empresa");
            } else if (principal == null) {
                //No hace falta comprobar nada ya que no hay usuario
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un usuario de una empresa");
            }

            businessTrue(this.titulado == null, "El titulado está prohibido para usuarios de empresa");
            businessTrue(this.centro == null, "El centro está prohibido para usuarios de empresa");

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                businessTrue(this.empresa != null, "La empresa es requerida para usuarios de una empresa");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                businessTrue(this.empresa != null, "La empresa es requerida para usuarios de la empresa");
                businessTrue(this.getEmpresa().getIdEmpresa() == principal.getEmpresa().getIdEmpresa(), "La empresa debe ser la misma empresa que la tuya");
            } else {
                businessTrue(this.empresa == null, "La empresa está prohibida al insertar el usuario");
            }

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El usuario de la empresa debe estar aceptado al insertarlo el administrador");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El usuario de la empresa debe estar aceptado al insertarlo otro usuario de la empresa");
            } else {
                businessTrue(this.estadoUsuario == null, "Es estado debe estar vacio al no haber empresa");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateEmpresaAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckUpdateEmpresaAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        if ((this.getTipoUsuario() == TipoUsuario.EMPRESA)) {

            //El principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                if (principal.getIdIdentity() != this.getIdIdentity()) {
                    securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                    securityTrue(principal.getEmpresa() != null, "Ya debes pertenecer a una empresa");
                }
            } else {
                throw new BusinessSecurityException("Solo un usuario de la empresa o los administradores pueden añadir otros usuarios de la empresa");
            }

            businessTrue(this.titulado == null, "El titulado está prohibido para usuarios de empresa");
            businessTrue(this.centro == null, "El centro está prohibido para usuarios de empresa");

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                businessTrue(this.empresa != null, "La empresa es requerida para usuarios de una empresa");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                businessTrue(this.empresa != null, "La empresa es requerida para usuarios de la empresa");
                businessTrue(this.getEmpresa().getIdEmpresa() == principal.getEmpresa().getIdEmpresa(), "La empresa debe ser la misma empresa que la tuya");
            } else {
                throw new RuntimeException("No puedes actualizar el usuario , pero ya se había comprobado asi que esto es un error");
            }

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El usuario de la empresa debe estar aceptado al insertarlo el administrador");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El usuario de la empresa debe estar aceptado al insertarlo otro usuario de la empresa");
            } else {
                businessTrue(this.estadoUsuario == null, "Es estado debe estar vacio al no haber empresa");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertTituladoAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertTituladoAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        if ((this.getTipoUsuario() == TipoUsuario.TITULADO)) {

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if (principal == null) {
                //No hace falta comprobar nada ya que no hay usuario pero se permite insertat sin principal
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un titulado");
            }

            businessTrue(this.empresa == null, "La empresa está prohibida para titulados");
            businessTrue(this.centro == null, "El centro está prohibido para titulados");
            businessTrue(this.titulado == null, "El titulado está prohibido al insertar el usuario");

            if (this.titulado == null) {
                businessTrue(this.estadoUsuario == null, "Sin titulado no puede haber estado");
            } else {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El titulado debe estar aceptado");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateTituladoAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateTituladoAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((this.getTipoUsuario() == TipoUsuario.TITULADO)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.TITULADO)) {
                securityTrue(principal.getIdIdentity() == this.getIdIdentity(), "No puedes actualizar a un titulado que no sea tu mismo");
            } else {
                throw new BusinessSecurityException("Solo el titulado o los administradores pueden actualizar al titulado");
            }

            businessTrue(this.empresa == null, "La empresa está prohibida para titulados");
            businessTrue(this.centro == null, "El centro está prohibido para titulados");

            if ((this.getTitulado() != null) && (usuarioOriginal.getTitulado() != null)) {
                businessTrue(this.getTitulado().getIdTitulado() != usuarioOriginal.getTitulado().getIdTitulado(), "No es posible cambiar de titulado");
            } else if ((this.getTitulado() == null) && (usuarioOriginal.getTitulado() != null)) {
                throw new BusinessException("No es posible quitar el titulado");
            } else if ((this.getTitulado() != null) && (usuarioOriginal.getTitulado() == null)) {
                //No pasa nada se le ha puesto el titulado
            } else if ((this.getTitulado() == null) && (usuarioOriginal.getTitulado() == null)) {
                //No pasa nada sigue sin titulado
            } else {
                throw new BusinessException("Error de lógica:" + (this.getTitulado()) + "," + (usuarioOriginal.getTitulado()));
            }

            if (this.titulado == null) {
                businessTrue(this.estadoUsuario == null, "Sin titulado no puede haber estado");
            } else {
                businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "El titulado debe estar aceptado");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertAdministradorAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertAdministradorAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        if ((this.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else {
                throw new BusinessSecurityException("Solo los administradores pueden insertar otro administrador");
            }

            businessTrue(this.empresa == null, "La empresa está prohibida para los administradores");
            businessTrue(this.centro == null, "El centro está prohibido para los administradores");
            businessTrue(this.titulado == null, "El titulado está prohibido los administradores");
            businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "Los administradores siempre deben estar aceptados");

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateAdministradorAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateAdministradorAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        if ((this.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else {
                throw new BusinessSecurityException("Solo los administradores pueden actualizar otro administrador");
            }

            businessTrue(this.empresa == null, "La empresa está prohibida para los administradores");
            businessTrue(this.centro == null, "El centro está prohibido para los administradores");
            businessTrue(this.titulado == null, "El titulado está prohibido los administradores");
            businessTrue(this.estadoUsuario == EstadoUsuario.ACEPTADO, "Los administradores siempre deben estar aceptados");

        }

        return true;

    }

    @ConstraintRule(message = "No es posible añadir usuarios desarrolladores", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isDesarrollador() {
        if (this.getTipoUsuario() == TipoUsuario.DESARROLLADOR) {
            return false;
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "No es posible modificar el tipo del usuario de '${originalEntity?.tipoUsuario?.toString()}' a '${entity?.tipoUsuario?.toString()}'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isModificadoTipoUsuario(RuleContext<Usuario> ruleContext) {
        if (ruleContext.getOriginalEntity().getTipoUsuario() != ruleContext.getEntity().getTipoUsuario()) {
            return false;
        } else {
            return true;
        }
    }

    @ActionRule(groups = RuleGroupPredefined.PreInsert.class)
    private void estableceFechaCreacion() {
        this.fecha = new Date();
    }

    @ActionRule(groups = RuleGroupPredefined.PostRead.class)
    private void quitarHashPassword() {
        //Nunca se retorna el Hash de la contraseña
        this.setPassword(null);
    }

    /**
     * @return the titulado
     */
    public Titulado getTitulado() {
        return titulado;
    }

    /**
     * @param titulado the titulado to set
     */
    public void setTitulado(Titulado titulado) {
        this.titulado = titulado;
    }

    /**
     * @return the eMail
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the eMail to set
     */
    public void setEmail(String email) {
        this.email = email;
        this.login = email;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.name = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the tipoUsuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * @return the centro
     */
    public Centro getCentro() {
        return centro;
    }

    /**
     * @param centro the centro to set
     */
    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the estadoUsuario
     */
    public EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    /**
     * @param estadoUsuario the estadoUsuario to set
     */
    public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    /**
     * @return the validadoEMail
     */
    public boolean isValidadoEmail() {
        return validadoEmail;
    }

    /**
     * @param validadoEmail the validadoEmail to set
     */
    public void setValidadoEmail(boolean validadoEmail) {
        this.validadoEmail = validadoEmail;
    }

    /**
     * @return the claveValidacionEmail
     */
    public String getClaveValidacionEmail() {
        return claveValidacionEmail;
    }

    /**
     * @param claveValidacionEmail the claveValidacionEmail to set
     */
    public void setClaveValidacionEmail(String claveValidacionEmail) {
        this.claveValidacionEmail = claveValidacionEmail;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
}
