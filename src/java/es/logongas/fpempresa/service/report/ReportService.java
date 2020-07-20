/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
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

package es.logongas.fpempresa.service.report;

import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.Service;
import java.util.Map;

/**
 * Servicio de Informes
 * @author logongas
 */
public interface ReportService extends Service {
    
    byte[] exportToPdf(DataSession dataSession,String reportName,Map<String, Object> parameters);
    
}
