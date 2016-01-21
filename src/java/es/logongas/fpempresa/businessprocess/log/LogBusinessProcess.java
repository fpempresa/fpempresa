/**
 * FPempresa Copyright (C) 2016 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.businessprocess.log;

import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;

/**
 *
 * @author logongas
 */
public interface LogBusinessProcess {
    
    ServerLogConfig getLogLevel(GetLogLevelArguments getLogLevelArguments);
    ServerLogConfig setLogLevel(SetLogLevelArguments setLogLevelArguments);
    
    public class GetLogLevelArguments extends BusinessProcess.BusinessProcessArguments {


        public GetLogLevelArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);
        }

    }
    
    public class SetLogLevelArguments extends BusinessProcess.BusinessProcessArguments {

        final public ServerLogConfig serverLogConfig;

        public SetLogLevelArguments(Principal principal, DataSession dataSession, ServerLogConfig serverLogConfig) {
            super(principal, dataSession);
            this.serverLogConfig = serverLogConfig;
        }

    }    
    
}
