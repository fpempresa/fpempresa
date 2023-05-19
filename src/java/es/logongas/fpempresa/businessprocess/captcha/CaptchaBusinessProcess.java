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
package es.logongas.fpempresa.businessprocess.captcha;

import es.logongas.fpempresa.modelo.captcha.Captcha;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;

/**
 *
 * @author logongas
 */
public interface CaptchaBusinessProcess extends BusinessProcess<Captcha> {
    byte[] getImage(CaptchaBusinessProcess.GetImageArguments imageArguments) throws BusinessException; 
    String getKeyCaptcha(GetKeyCaptchaArguments keyCaptchaArguments) throws BusinessException;
    
    public static class GetImageArguments extends BusinessProcess.BusinessProcessArguments {

        final public String keyCaptcha;
        
        public GetImageArguments(Principal principal, DataSession dataSession, String keyCaptcha) {
            super(principal, dataSession);
            this.keyCaptcha = keyCaptcha;            
        }
    }
    
    public static class GetKeyCaptchaArguments extends BusinessProcess.BusinessProcessArguments {

        
        public GetKeyCaptchaArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);            
        }
    }    

    
}
