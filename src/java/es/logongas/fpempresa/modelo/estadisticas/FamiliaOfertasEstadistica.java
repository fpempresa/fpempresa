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

import es.logongas.fpempresa.modelo.educacion.Familia;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author logongas
 */
public class FamiliaOfertasEstadistica extends Familia {

    private final List<OfertaEstadistica> ofertasEstadistica;
    

    public FamiliaOfertasEstadistica(int idFamilia, String descripcion) {
        super(idFamilia, descripcion);
        this.ofertasEstadistica=new ArrayList<>();
    }    


    /**
     * @return the ofertasEstadistica
     */
    public List<OfertaEstadistica> getOfertasEstadistica() {
        return ofertasEstadistica;
    }

    
    
}
