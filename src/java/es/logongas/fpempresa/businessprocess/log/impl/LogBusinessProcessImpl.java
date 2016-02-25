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
package es.logongas.fpempresa.businessprocess.log.impl;

import es.logongas.fpempresa.businessprocess.log.LogBusinessProcess;
import es.logongas.fpempresa.businessprocess.log.ServerLogConfig;
import es.logongas.ix3.core.Principal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 *
 * @author logongas
 */
public class LogBusinessProcessImpl implements LogBusinessProcess {

    private static final String APP_LOGGER_NAME = "es.logongas";
    private static final String SQL_LOGGER_NAME = "org.hibernate.SQL";

    @Override
    public ServerLogConfig getLogLevel(GetLogLevelArguments getLogLevelArguments) {
        ServerLogConfig serverLogConfig = new ServerLogConfig();
        serverLogConfig.setAppLevel(getLoggerConfig(APP_LOGGER_NAME).getLevel().name());
        serverLogConfig.setDatabaseLevel(getLoggerConfig(SQL_LOGGER_NAME).getLevel().name());

        return serverLogConfig;
    }

    @Override
    public ServerLogConfig setLogLevel(SetLogLevelArguments setLogLevelArguments) {
        if (setLogLevelArguments.serverLogConfig.getAppLevel() != null) {
            Configurator.setLevel(APP_LOGGER_NAME, Level.toLevel(setLogLevelArguments.serverLogConfig.getAppLevel()));
        }
        if (setLogLevelArguments.serverLogConfig.getDatabaseLevel() != null) {
            Configurator.setLevel(SQL_LOGGER_NAME, Level.toLevel(setLogLevelArguments.serverLogConfig.getDatabaseLevel()));
        }

        ServerLogConfig outServerLogConfig = new ServerLogConfig();
        outServerLogConfig.setAppLevel(getLoggerConfig(APP_LOGGER_NAME).getLevel().name());
        outServerLogConfig.setDatabaseLevel(getLoggerConfig(SQL_LOGGER_NAME).getLevel().name());
        
        return outServerLogConfig;

    }

    private LoggerConfig getLoggerConfig(String name) {
        LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        LoggerConfig loggerConfig = loggerContext.getConfiguration().getLoggerConfig(name);

        return loggerConfig;
    }
}
