/**
 * FPempresa Copyright (C) 2020 Lorenzo González
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



package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.businessprocess.empresa.OfertaCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.exception.ExceptionHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OfertaController {
  
    @Autowired
    private MetaDataFactory metaDataFactory;

    @Autowired
    private CRUDServiceFactory crudServiceFactory;
    @Autowired
    private CRUDBusinessProcessFactory crudBusinessProcessFactory;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private ExceptionHelper exceptionHelper; 
    @Autowired
    private DataSessionFactory dataSessionFactory;


    @RequestMapping(value = {"{path}/Oferta/{idOferta}/notificacionOferta"}, method = RequestMethod.PATCH, produces = "application/json")
    public void notificacionOferta(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idOferta") int idOferta) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            CRUDService<Oferta, Integer> ofertaCrudService = crudServiceFactory.getService(Oferta.class);
            Oferta oferta = ofertaCrudService.read(dataSession, idOferta);

            OfertaCRUDBusinessProcess ofertaCRUDBusinessProcess = (OfertaCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Oferta.class);

            ofertaCRUDBusinessProcess.notificacionOferta(new OfertaCRUDBusinessProcess.NotificacionOfertaArguments(principal, dataSession, oferta));

            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }

    }    
    
    @RequestMapping(value = {"/{path}/Oferta/cerrarOferta/{idOferta:.+}/{publicToken:.+}"}, method = RequestMethod.POST)
    public void cerrarOferta(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idOferta") int idOferta, @PathVariable("publicToken") String publicToken) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            
            CRUDService<Oferta, Integer> ofertaCrudService = crudServiceFactory.getService(Oferta.class);
            Oferta oferta = ofertaCrudService.read(dataSession, idOferta);
            
            OfertaCRUDBusinessProcess ofertaCRUDBusinessProcess = (OfertaCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Oferta.class);
            ofertaCRUDBusinessProcess.cerrarOferta(new OfertaCRUDBusinessProcess.CerrarOfertaArguments(principal, dataSession, oferta, publicToken));

            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    } 
}
