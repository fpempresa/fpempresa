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
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.businessprocess.empresa.CandidatoCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.json.JsonFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.MimeType;
import es.logongas.ix3.web.util.exception.ExceptionHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class CandidatoController {

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
    @Autowired
    private JsonFactory jsonFactory;

    @RequestMapping(value = {"/{path}/Candidato/{idCandidato}/foto"}, method = RequestMethod.GET)
    public void getFoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCandidato") int idCandidato) throws BusinessException {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            CRUDService<Candidato, Integer> candidatoCrudService = crudServiceFactory.getService(Candidato.class);
            Candidato candidato = candidatoCrudService.read(dataSession, idCandidato);

            CandidatoCRUDBusinessProcess candidatoCRUDBusinessProcess = (CandidatoCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Candidato.class);

            byte[] foto = candidatoCRUDBusinessProcess.getFotoCandidato(new CandidatoCRUDBusinessProcess.FotoCandidatoArguments(principal, dataSession, candidato));

            controllerHelper.objectToHttpResponse(new HttpResult(null, foto, 200, false, null, MimeType.OCTET_STREAM), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }

}
