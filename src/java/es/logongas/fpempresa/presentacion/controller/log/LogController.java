/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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
package es.logongas.fpempresa.presentacion.controller.log;

import es.logongas.fpempresa.businessprocess.log.LogBusinessProcess;
import es.logongas.fpempresa.businessprocess.log.ServerLogConfig;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.web.json.JsonFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LogController {

    @Autowired
    private LogBusinessProcess logBusinessProcess;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private JsonFactory jsonFactory;

    @RequestMapping(value = {"/$log"}, method = RequestMethod.GET, produces = "application/json")
    public void getLogLevel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            ServerLogConfig serverLogConfig = logBusinessProcess.getLogLevel(new LogBusinessProcess.GetLogLevelArguments(principal, dataSession));

            controllerHelper.objectToHttpResponse(new HttpResult(serverLogConfig), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/$log"}, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void setLogLevel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonIn) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            ServerLogConfig inputServerLogConfig = (ServerLogConfig) jsonFactory.getJsonReader(ServerLogConfig.class).fromJson(jsonIn, dataSession);
            ServerLogConfig serverLogConfig = logBusinessProcess.setLogLevel(new LogBusinessProcess.SetLogLevelArguments(principal, dataSession, inputServerLogConfig));

            controllerHelper.objectToHttpResponse(new HttpResult(serverLogConfig), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }

    }

}
