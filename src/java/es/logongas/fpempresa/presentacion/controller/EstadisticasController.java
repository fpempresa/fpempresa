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
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.businessprocess.estadisticas.EstadisticasBusinessProcess;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logongas
 */
@Controller
public class EstadisticasController {

    private static final Log log = LogFactory.getLog(EstadisticasController.class);

    @Autowired
    CRUDServiceFactory crudServiceFactory;
    @Autowired
    private EstadisticasBusinessProcess estadisticasBusinessProcess;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = {"/{path}/Estadisticas/centro/{idCentro}"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Centro centro = crudServiceFactory.getService(Centro.class).read(dataSession, idCentro);

            Estadisticas estadisticas = estadisticasBusinessProcess.getEstadisticasCentro(new EstadisticasBusinessProcess.GetEstadisticasCentroArguments(principal, dataSession, centro));
            controllerHelper.objectToHttpResponse(new HttpResult(estadisticas), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/{path}/Estadisticas/empresa/{idEmpresa}"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasEmpresa(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idEmpresa") int idEmpresa) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Empresa empresa = crudServiceFactory.getService(Empresa.class).read(dataSession, idEmpresa);

            Estadisticas estadisticas = estadisticasBusinessProcess.getEstadisticasEmpresa(new EstadisticasBusinessProcess.GetEstadisticasEmpresaArguments(principal, dataSession, empresa));

            controllerHelper.objectToHttpResponse(new HttpResult(estadisticas), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }

    @RequestMapping(value = {"/{path}/Estadisticas/administrador"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasAdministrador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Estadisticas estadisticas = estadisticasBusinessProcess.getEstadisticasAdministrador(new EstadisticasBusinessProcess.GetEstadisticasAdministradorArguments(principal, dataSession));

            controllerHelper.objectToHttpResponse(new HttpResult(estadisticas), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }

    @RequestMapping(value = {"/{path}/Estadisticas/publicas"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasPublicas(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            Estadisticas estadisticas = estadisticasBusinessProcess.getEstadisticasPublicas(new EstadisticasBusinessProcess.GetEstadisticasPublicasArguments(principal, dataSession));
            controllerHelper.objectToHttpResponse(new HttpResult(estadisticas), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }
}
