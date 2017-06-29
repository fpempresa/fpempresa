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

import es.logongas.ix3.core.annotations.Label;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Es cada uno de los trabajos que ha tenido un titulado
 *
 * @author Lorenzo
 */
public class ExperienciaLaboral {

    private int idExperienciaLaboral;

    @NotNull
    private Titulado titulado;

    @NotBlank
    @Label("Nombre de la empresa")
    private String nombreEmpresa;

    @Past
    @es.logongas.ix3.core.annotations.Date
    @Label("Fecha Inicio")
    private Date fechaInicio;

    @Past
    @es.logongas.ix3.core.annotations.Date
    @Label("Fecha final")
    private Date fechaFin;

    @Label("Puesto desempeñado")
    @NotBlank
    @Size(max = 255)
    private String puestoTrabajo;

    @Label("Tareas realizadas en el puesto desempeñado")
    @Size(max = 255)
    private String descripcion;

    /**
     * @return the idExperienciaLaboral
     */
    public int getIdExperienciaLaboral() {
        return idExperienciaLaboral;
    }

    /**
     * @param idExperienciaLaboral the idExperienciaLaboral to set
     */
    public void setIdExperienciaLaboral(int idExperienciaLaboral) {
        this.idExperienciaLaboral = idExperienciaLaboral;
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
     * @return the nombreEmpresa
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * @param nombreEmpresa the nombreEmpresa to set
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the puestoTrabajo
     */
    public String getPuestoTrabajo() {
        return puestoTrabajo;
    }

    /**
     * @param puestoTrabajo the puestoTrabajo to set
     */
    public void setPuestoTrabajo(String puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
