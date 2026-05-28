/**
 *   FPempresa
 *   Copyright (C) 2026  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.presentacion.controller.email;

import es.logongas.fpempresa.businessprocess.email.EmailBusinessProcess;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.web.json.JsonFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.exception.ExceptionHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmailController {

    @Autowired
    private EmailBusinessProcess emailBusinessProcess;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private ExceptionHelper exceptionHelper;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private JsonFactory jsonFactory;

    @RequestMapping(value = {"/administrador/email"}, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void sendEmail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonIn) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            SendEmailData sendEmailData = (SendEmailData) jsonFactory.getJsonReader(SendEmailData.class).fromJson(jsonIn, dataSession);
            emailBusinessProcess.sendEmail(new EmailBusinessProcess.SendEmailArguments(principal, dataSession, sendEmailData.getTo(), sendEmailData.getSubject(), sendEmailData.getBody()));

            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }

}
