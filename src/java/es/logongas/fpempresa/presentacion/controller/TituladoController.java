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

import es.logongas.fpempresa.businessprocess.titulado.TituladoCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.BusinessException;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GnommoStudios
 */
@Controller
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
    public void importarTitulados(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("file") final MultipartFile multipartFile) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            if (multipartFile.isEmpty()) {
                throw new BusinessException("No has subido ningún fichero");
            }
            TituladoCRUDBusinessProcess tituladoCRUDBusinessProcess = (TituladoCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Titulado.class);
            tituladoCRUDBusinessProcess.importarTitulados(new TituladoCRUDBusinessProcess.ImportarTituladosArguments(principal, dataSession, multipartFile));

            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }
}
