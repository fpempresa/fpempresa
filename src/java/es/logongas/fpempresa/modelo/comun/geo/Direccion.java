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

package es.logongas.fpempresa.modelo.comun.geo;

import es.logongas.ix3.core.annotations.Label;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Direccion {
    
    @NotBlank
    @Label("Dirección")
    @Size(min = 3, max = 255)
    private String datosDireccion;
   
    @NotNull
    private Municipio municipio;

    /**
     * @return the datosDireccion
     */
    public String getDatosDireccion() {
        return datosDireccion;
    }

    /**
     * @param datosDireccion the datosDireccion to set
     */
    public void setDatosDireccion(String datosDireccion) {
        this.datosDireccion = datosDireccion;
    }

    /**
     * @return the municipio
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }



}
