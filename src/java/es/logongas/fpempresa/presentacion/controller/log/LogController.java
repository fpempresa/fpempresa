/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.presentacion.controller.log;

import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.web.controllers.AbstractRestController;
import es.logongas.ix3.web.controllers.Command;
import es.logongas.ix3.web.controllers.CommandResult;
import es.logongas.ix3.web.controllers.endpoint.EndPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Cambia el nivel de log
 *
 * @author logongas
 */
@Controller
public class LogController extends AbstractRestController {

    private static final String APP_LOGGER_NAME = "es.logongas";
    private static final String SQL_LOGGER_NAME = "org.hibernate.SQL";

    @RequestMapping(value = {"/$log"}, method = RequestMethod.GET, produces = "application/json")
    public void getLogLevel(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {

        restMethod(httpServletRequest, httpServletResponse, new Command() {

            @Override
            public CommandResult run(EndPoint endPoint) throws Exception, BusinessException {

                ServerLogConfig serverLogConfig=new ServerLogConfig();
                serverLogConfig.setAppLevel(getLoggerConfig(APP_LOGGER_NAME).getLevel().name());
                serverLogConfig.setDatabaseLevel(getLoggerConfig(SQL_LOGGER_NAME).getLevel().name());
                        
                
                return new CommandResult(serverLogConfig);
            }
        });
    }

    @RequestMapping(value = {"/$log"}, method = RequestMethod.PUT,consumes="application/json", produces = "application/json")
    public void setLogLevel(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,final @RequestBody String jsonIn) {

        restMethod(httpServletRequest, httpServletResponse, new Command() {

            @Override
            public CommandResult run(EndPoint endPoint) throws Exception, BusinessException {

                ServerLogConfig inputServerLogConfig=(ServerLogConfig)jsonFactory.getJsonReader(ServerLogConfig.class).fromJson(jsonIn);
                
                if (inputServerLogConfig.getAppLevel()!=null) {
                    Configurator.setLevel(APP_LOGGER_NAME,Level.toLevel(inputServerLogConfig.getAppLevel()));
                }
                if (inputServerLogConfig.getDatabaseLevel()!=null) {
                    Configurator.setLevel(SQL_LOGGER_NAME, Level.toLevel(inputServerLogConfig.getDatabaseLevel()));
                }                
                
                ServerLogConfig outputServerLogConfig=new ServerLogConfig();
                outputServerLogConfig.setAppLevel(getLoggerConfig(APP_LOGGER_NAME).getLevel().name());
                outputServerLogConfig.setDatabaseLevel(getLoggerConfig(SQL_LOGGER_NAME).getLevel().name());
                        
                return new CommandResult(outputServerLogConfig);
            }
        });
    }

    private LoggerConfig getLoggerConfig(String name) {
        LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        LoggerConfig loggerConfig = loggerContext.getConfiguration().getLoggerConfig(name);

        return loggerConfig;
    }

   
}
