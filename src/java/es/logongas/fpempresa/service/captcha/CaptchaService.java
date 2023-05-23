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
package es.logongas.fpempresa.service.captcha;

import es.logongas.fpempresa.modelo.captcha.Captcha;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.Service;

/**
 *
 * @author logongas
 */
public interface CaptchaService extends Service<Captcha> {
    
    String getKeyCaptcha();
    byte[] getImage(String keyCaptcha);
    boolean solveChallenge (DataSession dataSession, String keyCaptcha, String word) throws BusinessException,CatpchaAlreadyUsedException;
    void storeKeyCaptcha (DataSession dataSession, String keyCaptcha) throws BusinessException;
}
