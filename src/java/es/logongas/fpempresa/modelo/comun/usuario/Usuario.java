/**
 * FPempresa Copyright (C) 2014 Lorenzo González
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
package es.logongas.fpempresa.modelo.comun.usuario;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.security.model.User;
import es.logongas.ix3.core.annotations.Label;
import es.logongas.ix3.core.Principal;
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

    private String claveResetearContrasenya;

    private Date fechaClaveResetearContrasenya;

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

    public String getClaveResetearContrasenya() {
        return claveResetearContrasenya;
    }

    public void setClaveResetearContrasenya(String claveResetearContrasenya) {
        this.claveResetearContrasenya = claveResetearContrasenya;
    }

    public Date getFechaClaveResetearContrasenya() {
        return fechaClaveResetearContrasenya;
    }

    public void setFechaClaveResetearContrasenya(Date fechaClaveResetearContrasenya) {
        this.fechaClaveResetearContrasenya = fechaClaveResetearContrasenya;
    }

}
