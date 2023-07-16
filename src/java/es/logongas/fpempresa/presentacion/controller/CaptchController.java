/*
 * FPempresa Copyright (C) 2023 Lorenzo González
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

import es.logongas.fpempresa.businessprocess.captcha.CaptchaBusinessProcess;
import es.logongas.fpempresa.modelo.captcha.Captcha;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.MimeType;
import es.logongas.ix3.web.util.exception.ExceptionHelper;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logongas
 */
@Controller
public class CaptchController {
    
    @Autowired
    private DataSessionFactory dataSessionFactory;
    
    @Autowired
    private ControllerHelper controllerHelper;
    
    @Autowired
    private ExceptionHelper exceptionHelper;
    
    @Autowired
    private CaptchaBusinessProcess captchaBusinessProcess;
    
    @RequestMapping(value = {"/{path}/Captcha/image"}, method = RequestMethod.GET)
    public void getImageCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BusinessException {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            String keyCaptcha=httpServletRequest.getParameter("keyCaptcha");

            byte[] imageCaptcha = captchaBusinessProcess.getImage(new CaptchaBusinessProcess.GetImageArguments(principal, dataSession,keyCaptcha));

            controllerHelper.objectToHttpResponse(new HttpResult(null, imageCaptcha, 200, false, null, MimeType.OCTET_STREAM), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }
    
    @RequestMapping(value = {"/{path}/Captcha/keyCaptcha"}, method = RequestMethod.GET)
    public void getKeyCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BusinessException {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);


            Captcha captcha = captchaBusinessProcess.getCaptcha(new CaptchaBusinessProcess.GetCaptchaArguments(principal, dataSession));
            
            controllerHelper.objectToHttpResponse(new HttpResult(captcha), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }    
    
}
