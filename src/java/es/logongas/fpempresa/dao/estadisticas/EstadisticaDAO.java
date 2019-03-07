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
package es.logongas.fpempresa.dao.estadisticas;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaEstadistica;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 * DAO para obtener lso datos de las estedísticas por familia
 *
 * @author logongas
 */
public interface EstadisticaDAO {

    List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession);

    List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession, Empresa empresa);

    List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin);

    List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession);

    List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession, Empresa empresa);

    List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin);

    List<FamiliaEstadistica> getTitulosFPGroupByFamilia(DataSession dataSession);

    List<FamiliaEstadistica> getTitulosFPGroupByFamilia(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin);
    
    Integer getSumCentros(DataSession dataSession);

    public Integer getSumEmpresas(DataSession dataSession);

}
