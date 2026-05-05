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
package es.logongas.fpempresa.businessprocess.log.impl;

import es.logongas.fpempresa.modelo.log.LogFile;
import es.logongas.fpempresa.service.log.LogFileService;
import es.logongas.ix3.core.BusinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import es.logongas.fpempresa.businessprocess.log.LogFileBusinessProcess;

/**
 *
 * @author logongas
 */
public class LogFileBusinessProcessImpl implements LogFileBusinessProcess {

    private Class entityType;

    @Autowired
    private LogFileService logFileService;

    @Override
    public List<LogFile> search(SearchLogFilesArguments searchLogFilesArguments) throws BusinessException {
        return logFileService.search(searchLogFilesArguments.dataSession);
    }

    @Override
    public LogFile read(ReadLogFileArguments readLogFileArguments) throws BusinessException {
        return logFileService.read(readLogFileArguments.dataSession, readLogFileArguments.idLogFile);
    }

    @Override
    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    @Override
    public Class getEntityType() {
        return this.entityType;
    }

}
