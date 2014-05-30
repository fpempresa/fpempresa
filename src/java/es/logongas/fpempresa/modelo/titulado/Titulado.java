/**
 *   FPempresa
 *   Copyright (C) 2014  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.modelo.titulado;

import es.logongas.fpempresa.modelo.comun.Direccion;
import es.logongas.fpempresa.modelo.comun.Usuario;
import es.logongas.ix3.core.annotations.Caption;
import java.util.Date;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Lorenzo
 */
public class Titulado {
    private int idTitulado;
    
    @NotNull
    private Usuario usuario;
    
    @NotNull
    @Past
    @Caption("Fecha de nacimiento")
    private Date fechaNacimiento;
    
    @NotNull
    @Valid
    private Direccion direccion;
    
    @Pattern(regexp = "[0-9]{9}| {0}")
    private String telefono;
    
    @Pattern( regexp = "[0-9]{9}| {0}")
    @Caption("Telefono alternativo")
    private String telefonoAlternativo;
    
    @NotNull
    @Caption("Tipo de documento")
    private TipoDocumento tipoDocumento;
    
    @NotEmpty
    @Caption("Nº de documento")
    private String numeroDocumento;
    
    private Set<TituloIdioma> titulosIdiomas;
    private Set<ExperienciaLaboral> experienciasLaborales;
    private Set<FormacionAcademica> formacionesAcademicas;
    
    public Titulado() {
    }
    
    /**
     * @return the idTitulado
     */
    public int getIdTitulado() {
        return idTitulado;
    }

    /**
     * @param idTitulado the idTitulado to set
     */
    public void setIdTitulado(int idTitulado) {
        this.idTitulado = idTitulado;
    }

    /**
     * @return the user
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the user to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the direccion
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the telefonoAlternativo
     */
    public String getTelefonoAlternativo() {
        return telefonoAlternativo;
    }

    /**
     * @param telefonoAlternativo the telefonoAlternativo to set
     */
    public void setTelefonoAlternativo(String telefonoAlternativo) {
        this.telefonoAlternativo = telefonoAlternativo;
    }

    /**
     * @return the tipoDocumento
     */
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return the titulosIdioma
     */
    public Set<TituloIdioma> getTitulosIdiomas() {
        return titulosIdiomas;
    }

    /**
     * @param titulosIdiomas the titulosIdioma to set
     */
    public void setTitulosIdiomas(Set<TituloIdioma> titulosIdiomas) {
        this.titulosIdiomas = titulosIdiomas;
    }

    /**
     * @return the experienciasLaborales
     */
    public Set<ExperienciaLaboral> getExperienciasLaborales() {
        return experienciasLaborales;
    }

    /**
     * @param experienciasLaborales the experienciasLaborales to set
     */
    public void setExperienciasLaborales(Set<ExperienciaLaboral> experienciasLaborales) {
        this.experienciasLaborales = experienciasLaborales;
    }

    /**
     * @return the formacionesAcademicas
     */
    public Set<FormacionAcademica> getFormacionesAcademicas() {
        return formacionesAcademicas;
    }

    /**
     * @param formacionesAcademicas the formacionesAcademicas to set
     */
    public void setFormacionesAcademicas(Set<FormacionAcademica> formacionesAcademicas) {
        this.formacionesAcademicas = formacionesAcademicas;
    }

}
