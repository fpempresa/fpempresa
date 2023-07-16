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
package es.logongas.fpempresa.businessprocess.captcha.impl;

import es.logongas.fpempresa.businessprocess.captcha.CaptchaBusinessProcess;
import es.logongas.fpempresa.modelo.captcha.Captcha;
import es.logongas.fpempresa.service.captcha.CaptchaService;
import es.logongas.fpempresa.util.ImageUtil;
import es.logongas.ix3.core.BusinessException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class CaptchaBusinessProcessImpl implements CaptchaBusinessProcess {
    
    @Autowired    
    CaptchaService captchaService; 
    
    Class<Captcha> entityType;
    
    @Override
    public byte[] getImage(GetImageArguments imageArguments) throws BusinessException {
        byte[] imageCaptcha;
        
        if ((imageArguments.keyCaptcha == null) || imageArguments.keyCaptcha.trim().isEmpty()) {
            imageCaptcha=ImageUtil.getOnePixelWhitePngImage();
        } else {
            imageCaptcha=captchaService.getImage(imageArguments.keyCaptcha);
        }

        return imageCaptcha;
    }
    
    @Override
    public Captcha getCaptcha(GetCaptchaArguments captchaArguments) throws BusinessException {
        String keyCaptcha=captchaService.getKeyCaptcha();


        Captcha captcha =new Captcha();
        captcha.setFecha(new Date());
        captcha.setKeyCaptcha(keyCaptcha);
        
        return captcha;
    }    

    @Override
    public void setEntityType(Class<Captcha> entityType) {
        this.entityType=entityType;
    }

    @Override
    public Class<Captcha> getEntityType() {
        return this.entityType;
    }
    
}
