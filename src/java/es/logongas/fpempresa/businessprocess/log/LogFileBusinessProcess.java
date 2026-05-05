/**
 * FPempresa Copyright (C) 2026 Lorenzo González
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
package es.logongas.fpempresa.businessprocess.log;

import es.logongas.fpempresa.modelo.log.LogFile;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface LogFileBusinessProcess extends BusinessProcess {

    List<LogFile> search(SearchLogFilesArguments searchLogFilesArguments) throws BusinessException;

    LogFile read(ReadLogFileArguments readLogFileArguments) throws BusinessException;

    public class SearchLogFilesArguments extends BusinessProcess.BusinessProcessArguments {

        public SearchLogFilesArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);
        }

    }

    public class ReadLogFileArguments extends BusinessProcess.BusinessProcessArguments {

        final public int idLogFile;

        public ReadLogFileArguments(Principal principal, DataSession dataSession, int idLogFile) {
            super(principal, dataSession);
            this.idLogFile = idLogFile;
        }

    }

}
