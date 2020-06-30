/**
 * FPempresa Copyright (C) 2020 Lorenzo González
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
package es.logongas.fpempresa.modelo.estadisticas;

import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import java.util.Date;

/**
 *
 * @author logongas
 */
public class OfertaEstadistica {
    
    private final int idOferta;
    private final String puesto;
    private final Provincia provincia;
    private final Date fecha;

    public OfertaEstadistica(Oferta oferta) {
        this.idOferta = oferta.getIdOferta();
        this.puesto = oferta.getPuesto();
        this.provincia = oferta.getMunicipio().getProvincia();
        this.fecha=oferta.getFecha();
    }

    /**
     * @return the idOferta
     */
    public int getIdOferta() {
        return idOferta;
    }    
    
    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @return the provincia
     */
    public Provincia getProvincia() {
        return provincia;
    }
    
    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }    
    
    
}
