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


package es.logongas.fpempresa.modelo.centro;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.ix3.core.annotations.Label;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Centros de FP
 * @author Lorenzo
 */
public class Centro {
    
    @JsonProperty("idCentro")
    private int idCentro;
  
    private String  nombre;
    
    @JsonProperty("direccion")
    @Valid
    private Direccion direccion;
    
    @Label("Pertenencia a FPempresa")
    private EstadoCentro estadoCentro;

    @JsonProperty("contacto")
    @Valid
    @Label("Contacto")
    private Contacto contacto;       
    
    public Centro() {
    }

    @Override
    public String toString() {
        return nombre;
    }

    
    
    /**
     * @return the idCentro
     */
    public int getIdCentro() {
        return idCentro;
    }

    /**
     * @param idCentro the idCentro to set
     */
    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
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
     * @return the estadoCentro
     */
    public EstadoCentro getEstadoCentro() {
        return estadoCentro;
    }

    /**
     * @param estadoCentro the estadoCentro to set
     */
    public void setEstadoCentro(EstadoCentro estadoCentro) {
        this.estadoCentro = estadoCentro;
    }

    /**
     * @return the contacto
     */
    public Contacto getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }




}
