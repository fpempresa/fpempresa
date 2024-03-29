/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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
package es.logongas.fpempresa.service.estadisticas;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.estadisticas.EstadisticasPrincipal;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.Service;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface EstadisticasService extends Service<Estadisticas> {

    Estadisticas getEstadisticasAdministrador(DataSession dataSession);

    Estadisticas getEstadisticasCentro(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin);

    Estadisticas getEstadisticasEmpresa(DataSession dataSession, Empresa empresa);

    Estadisticas getEstadisticasPublicas(DataSession dataSession);

    List<FamiliaOfertasEstadistica> getEstadisticasFamiliaOfertasPublicas(DataSession dataSession) throws BusinessException;
    
    EstadisticasPrincipal getEstadisticasPrincipal(DataSession dataSession);    
    
}
