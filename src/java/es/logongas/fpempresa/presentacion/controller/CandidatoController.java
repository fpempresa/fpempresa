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
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.metadata.MetaData;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.controllers.helper.AbstractRestController;
import es.logongas.ix3.web.controllers.command.Command;
import es.logongas.ix3.web.controllers.command.CommandResult;
import es.logongas.ix3.web.controllers.command.MimeType;
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
public class CandidatoController extends AbstractRestController {

    @Autowired
    private CRUDServiceFactory crudServiceFactory;

    @RequestMapping(value = {"/{path}/Candidato/{idCandidato}/foto"}, method = RequestMethod.GET)
    public void getFoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCandidato") int idCandidato) throws BusinessException {

        CRUDService<Candidato, Integer> candidatoCrudService = (CRUDService<Candidato, Integer>) crudServiceFactory.getService(Candidato.class);
        Candidato candidato = candidatoCrudService.read(idCandidato);

        restMethod(httpServletRequest, httpServletResponse, "getFoto", null, new Command() {
            public int idCandidato;
            public Candidato candidato;

            public Command inicialize(int idCandidato, Candidato candidato) {
                this.idCandidato = idCandidato;
                this.candidato = candidato;
                return this;
            }

            @Override
            public CommandResult run() throws Exception, BusinessException {
                return new CommandResult(null, candidato.getUsuario().getFoto(), 200, false, null, MimeType.OCTET_STREAM);
            }
        }.inicialize(idCandidato, candidato));
    }

}
