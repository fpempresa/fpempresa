/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.businessprocess.titulado.TituladoCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.json.JsonFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GnommoStudios
 */
public class TituladoController {

    @Autowired
    private CRUDServiceFactory crudServiceFactory;
    @Autowired
    private CRUDBusinessProcessFactory crudBusinessProcessFactory;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private JsonFactory jsonFactory;

    @RequestMapping(value = {"/{path}/importar-csv"}, method = RequestMethod.POST, produces = "application/json")
    public void importarTituladosCSV(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("file") final MultipartFile multipartFile) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            TituladoCRUDBusinessProcess tituladoCRUDBusinessProcess = (TituladoCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Titulado.class);
            tituladoCRUDBusinessProcess.importarTituladosCSV(new TituladoCRUDBusinessProcess.ImportarTituladosCSVArguments(principal, dataSession, multipartFile));
            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }
}
