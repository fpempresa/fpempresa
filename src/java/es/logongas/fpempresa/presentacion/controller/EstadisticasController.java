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
import es.logongas.fpempresa.modelo.comun.geo.ComunidadAutonoma;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadistica;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.estadisticas.EstadisticasPrincipal;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.GroupByEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.NombreEstadistica;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.exception.ExceptionHelper;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(EstadisticasController.class);

    @Autowired
    CRUDServiceFactory crudServiceFactory;
    @Autowired
    private EstadisticasBusinessProcess estadisticasBusinessProcess;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private ExceptionHelper exceptionHelper; 
    @Autowired
    private Conversion conversion;    
    @Autowired
    private ControllerUtil controllerUtil;
    
    @RequestMapping(value = {"/{path}/Estadisticas/centro/{idCentro}"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            
            Integer anyoInicio=(Integer)conversion.convertFromString(httpServletRequest.getParameter("anyoInicio"), Integer.class);
            Integer anyoFin=(Integer)conversion.convertFromString(httpServletRequest.getParameter("anyoFin"), Integer.class);
              
            Centro centro = crudServiceFactory.getService(Centro.class).read(dataSession, idCentro);

            Estadisticas estadisticas = estadisticasBusinessProcess.getEstadisticasCentro(new EstadisticasBusinessProcess.GetEstadisticasCentroArguments(principal, dataSession, centro,anyoInicio,anyoFin));
            controllerHelper.objectToHttpResponse(new HttpResult(estadisticas), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
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
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }

    @RequestMapping(value = {"/{path}/Estadisticas/administrador"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasAdministrador(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        CRUDService<ComunidadAutonoma, Integer> comunidadAutonomaService = crudServiceFactory.getService(ComunidadAutonoma.class);
        CRUDService<Provincia, Integer> provinciaService = crudServiceFactory.getService(Provincia.class);
        CRUDService<Familia, Integer> familiaService = crudServiceFactory.getService(Familia.class);
        CRUDService<Ciclo, Integer> cicloService = crudServiceFactory.getService(Ciclo.class);
        
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            
            NombreEstadistica nombreEstadistica=NombreEstadistica.valueOf(controllerHelper.getParameter(httpServletRequest, "nombreEstadistica"));
            GroupByEstadistica groupByEstadistica=GroupByEstadistica.valueOf(controllerHelper.getParameter(httpServletRequest, "groupByEstadistica"));
            
            Date filterDesde=controllerUtil.getDateParameter(httpServletRequest, "desde");
            Date filterHasta=controllerUtil.getDateParameter(httpServletRequest, "hasta");
            
            ComunidadAutonoma filterComunidadAutonoma=null;
            Integer idComunidadAutonoma=controllerUtil.getIntegerParameter(httpServletRequest, "comunidadAutonoma");
            if (idComunidadAutonoma!=null) {
                filterComunidadAutonoma=comunidadAutonomaService.read(dataSession, idComunidadAutonoma);
            }
            
            Provincia filterProvincia=null;
            Integer idProvincia=controllerUtil.getIntegerParameter(httpServletRequest, "provincia");
            if (idProvincia!=null) {
                filterProvincia=provinciaService.read(dataSession, idProvincia);
            }
            
            Familia filterFamilia=null;
            Integer idFamilia=controllerUtil.getIntegerParameter(httpServletRequest, "familia");
            if (idFamilia!=null) {
                filterFamilia=familiaService.read(dataSession, idFamilia);
            }

            Ciclo filterCiclo=null;
            Integer idCiclo=controllerUtil.getIntegerParameter(httpServletRequest, "ciclo");
            if (idCiclo!=null) {
                filterCiclo=cicloService.read(dataSession, idCiclo);
            }
            
            Estadistica estadistica = estadisticasBusinessProcess.getEstadisticasAdministrador(new EstadisticasBusinessProcess.GetEstadisticasAdministradorArguments(principal, dataSession, nombreEstadistica, groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo));

            controllerHelper.objectToHttpResponse(new HttpResult(estadistica), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }

    @RequestMapping(value = {"/{path}/Estadisticas/publicas"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasPublicas(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            Estadisticas estadisticas = estadisticasBusinessProcess.getEstadisticasPublicas(new EstadisticasBusinessProcess.GetEstadisticasPublicasArguments(principal, dataSession));
            controllerHelper.objectToHttpResponse(new HttpResult(estadisticas), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }
    
    @RequestMapping(value = {"/{path}/Estadisticas/familiasOfertas"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasOfertasPublicas(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            
            List<FamiliaOfertasEstadistica>  familiasOfertasEstadistica=estadisticasBusinessProcess.getEstadisticasFamiliaOfertasPublicas(new EstadisticasBusinessProcess.GetEstadisticasFamiliaOfertasPublicasArguments(principal, dataSession));
            controllerHelper.objectToHttpResponse(new HttpResult(familiasOfertasEstadistica), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }
    
    @RequestMapping(value = {"/{path}/Estadisticas/principal"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasPrincipal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            
            EstadisticasPrincipal estadisticasPrincipal=estadisticasBusinessProcess.getEstadisticasPrincipal(new EstadisticasBusinessProcess.GetEstadisticasPrincipalArguments(principal, dataSession));
            controllerHelper.objectToHttpResponse(new HttpResult(estadisticasPrincipal), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }    
    
}
