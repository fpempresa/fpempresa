/**
 * FPempresa Copyright (C) 2014 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.modelo.comun.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.logongas.ix3.core.annotations.ValuesList;
import javax.validation.constraints.NotNull;

/**
 * Entidad con las provincias.
 *
 * @author Lorenzo
 */
public class Provincia {

    @JsonProperty("idProvincia")
    private int idProvincia;

    private String descripcion;

    @JsonProperty("comunidadAutonoma")
    @NotNull
    @ValuesList(shortLength = true)
    private ComunidadAutonoma comunidadAutonoma;
    
    public Provincia() {
    }

    @Override
    public String toString() {
        return descripcion;
    }

    /**
     * @return the idProvincia
     */
    public int getIdProvincia() {
        return idProvincia;
    }

    /**
     * @param idProvincia the idProvincia to set
     */
    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
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

    /**
     * @return the comunidadAutonoma
     */
    public ComunidadAutonoma getComunidadAutonoma() {
        return comunidadAutonoma;
    }

    /**
     * @param comunidadAutonoma the comunidadAutonoma to set
     */
    public void setComunidadAutonoma(ComunidadAutonoma comunidadAutonoma) {
        this.comunidadAutonoma = comunidadAutonoma;
    }

}
