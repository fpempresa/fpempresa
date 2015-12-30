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
import es.logongas.ix3.security.authentication.Principal;
import es.logongas.ix3.service.rules.ActionRule;
import es.logongas.ix3.service.rules.ConstraintRule;
import es.logongas.ix3.service.rules.RuleContext;
import es.logongas.ix3.service.rules.RuleGroupPredefined;
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

    @ConstraintRule(message = "El centro es requerido si el usuario está aceptado")
    private boolean isCentroRequeridoSiUsuarioAceptado() {
        if ((this.getTipoUsuario() == TipoUsuario.CENTRO) && (this.getEstadoUsuario() == EstadoUsuario.ACEPTADO)) {
            if (this.centro == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "Un administrador siempre debe estar aceptado")
    private boolean isAdministradorAceptado() {
        if (this.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            if (this.getEstadoUsuario() == EstadoUsuario.ACEPTADO) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "No es posible añadir usuarios desarrolladores", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isDesarrollador() {
        if (this.getTipoUsuario() == TipoUsuario.DESARROLLADOR) {
            return false;
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "La empresa está prohibida si no es un usuario de una empresa")
    private boolean isEmpresaProhibidaSiUsuarioNoEmpresa() {
        if ((this.getTipoUsuario() != TipoUsuario.EMPRESA)) {
            if (this.empresa != null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "El centro está prohibido si no es un usuario de un centro")
    private boolean isCentroProhibidoSiUsuarioNoCentro() {
        if ((this.getTipoUsuario() != TipoUsuario.CENTRO)) {
            if (this.centro != null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "El titulado está prohibido si no es un usuario titulado")
    private boolean isTituladoProhibidoSiUsuarioNoTitulado() {
        if ((this.getTipoUsuario() != TipoUsuario.TITULADO)) {
            if (this.titulado != null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "No es posible modificar el tipo del usuario de '${originalEntity?.tipoUsuario?.toString()}' a '${entity?.tipoUsuario?.toString()}'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isModificadoTipoUsuario(RuleContext<Usuario> ruleContext) {
        if (ruleContext.getOriginalEntity().getTipoUsuario() != ruleContext.getEntity().getTipoUsuario()) {
            return false;
        }

        return true;
    }

    @ConstraintRule(message = "Error en el sistema de mensajes", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isModificadoEstadoUsuario(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        //REGLA SEGURIDAD:Comprobar si puede modificar el estado de un usuario
        if (usuarioOriginal.getEstadoUsuario() != usuario.getEstadoUsuario()) {

            if (principal.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                //Si el usuario no está aceptado no le dejamos cambiar el estado
                throw new BusinessException("No es posible modificar el estado del usuario ya que TU no estás aceptado");
            }

            if (usuario.getIdIdentity() == principal.getIdIdentity()) {
                throw new BusinessException("No te puedes modificar tu mismo el estado");
            }

            switch (usuario.getTipoUsuario()) {
                case ADMINISTRADOR:
                    throw new BusinessException("No es posible modificar el estado del administrador , ya que siempre es ACEPTADO");
                case CENTRO:
                    if ((principal.getTipoUsuario() == TipoUsuario.CENTRO)) {

                        if (usuario.getCentro().getIdCentro() != principal.getCentro().getIdCentro()) {
                            throw new BusinessException("No puedes modificar el estado de usuarios que sean de centros distintos al tuyo");
                        } else {
                            return true;
                        }

                    } else if (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                        return true;
                    } else {
                        throw new BusinessException("Solo un profesor y un administrador pueden cambiar el estado del usuario pero era un " + principal.getTipoUsuario());
                    }

                case EMPRESA:
                    throw new BusinessException("No es posible modificar el estado de un usuario de empresa , ya que siempre es ACEPTADO");
                case TITULADO:
                    throw new BusinessException("No es posible modificar el estado de un titulado , ya que siempre es ACEPTADO");
                default:
                    throw new RuntimeException("Tipo de usario desconocido:" + usuario.getTipoUsuario());
            }
        } else {
            return true;
        }
    }

    @ConstraintRule(message = "No se puede cambiar la empresa del usuario", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isModificadoEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if (usuarioOriginal.getTipoUsuario() == TipoUsuario.EMPRESA) {
            if (usuario.getEmpresa().getIdEmpresa() != usuarioOriginal.getEmpresa().getIdEmpresa()) {
                return false;
            } else {
                return true;
            }

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

    @ActionRule(groups = RuleGroupPredefined.PreInsert.class)
    private void estadoInicialDelUsuario(RuleContext<Usuario> ruleContext) {
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        switch (usuario.getTipoUsuario()) {
            case TITULADO:
                usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                break;
            case CENTRO:
                if (principal != null) {
                    if (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                        usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                    } else if (principal.getTipoUsuario() == TipoUsuario.CENTRO) {
                        if (principal.getCentro().getIdCentro() == usuario.getCentro().getIdCentro()) {
                            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                        } else {
                            usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
                        }
                    } else {
                        usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
                    }
                } else {
                    usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
                }

                break;
            case EMPRESA:
                usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                break;
            case ADMINISTRADOR:
                usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                break;
            default:
                break;
        }

    }

    @ActionRule(groups = RuleGroupPredefined.PreUpdate.class)
    private void pasarAPendienteAlCambiarDeCentro(RuleContext<Usuario> ruleContext) {
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();
        Usuario principal = (Usuario) ruleContext.getPrincipal();

        if (principal.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
            if (usuario.getTipoUsuario() == TipoUsuario.CENTRO) {
                if (((usuarioOriginal.getCentro() == null) && (usuario.getCentro() != null))
                        || ((usuarioOriginal.getCentro() != null) && (usuario.getCentro() == null))
                        || (usuarioOriginal.getCentro().getIdCentro() != usuario.getCentro().getIdCentro())) {
                    usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
                }
            }
        }
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
}
