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
package es.logongas.fpempresa.service.captcha.impl;

import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.modelo.captcha.Captcha;
import es.logongas.fpempresa.security.SecureKeyGenerator;
import es.logongas.fpempresa.service.captcha.CaptchaService;
import es.logongas.fpempresa.service.kernel.captcha.CaptchaKernelService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.dao.GenericDAO;
import es.logongas.ix3.web.security.jwt.Jwe;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class CaptchaServiceImpl implements CaptchaService {

    private static final int MAX_AGE_MINUTES=120;
    
    @Autowired
    CaptchaKernelService captchaKernelService;
    
    @Autowired
    Jwe jwe;
    
    @Autowired 
    protected DAOFactory daoFactory;
    
    Class<Captcha> entityType;
    
    @Override
    public String getKeyCaptcha() {
        String word=captchaKernelService.getWord();
        byte[] secretKey=getSecretKey();
        
        return jwe.getJwsCompactSerialization(word, secretKey);
        
    }

    @Override
    public byte[] getImage(String keyCaptcha) {
        byte[] secretKey=getSecretKey();
        
        String word=jwe.getPayloadFromJwsCompactSerialization(keyCaptcha, secretKey, MAX_AGE_MINUTES);
        
        return captchaKernelService.getImage(word);
    }

    @Override
    public boolean solveChallenge(DataSession dataSession,String keyCaptcha, String word) throws BusinessException {

        if ((keyCaptcha==null) || (keyCaptcha.trim().isEmpty())) {
            throw new RuntimeException("El keyCaptcha no puede estar vacio:"+keyCaptcha);
        }
        
        if (existsKeyCaptcha(dataSession, keyCaptcha)) {
            throw new RuntimeException("El keyCaptcha ya ha sido usado:"+keyCaptcha);
        }        
        
        
        this.storeKeyCaptcha(dataSession, keyCaptcha);
        
        byte[] secretKey=getSecretKey();
        String originalWord=jwe.getPayloadFromJwsCompactSerialization(keyCaptcha, secretKey, MAX_AGE_MINUTES);
        String wordWithoutSpaces=word.replaceAll("[^\\x21-\\x7E]", "");
        
        
        if (originalWord.equalsIgnoreCase(wordWithoutSpaces)==true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean existsKeyCaptcha(DataSession dataSession, String keyCaptcha) throws BusinessException {
        GenericDAO<Captcha,Integer> captchaDAO=daoFactory.getDAO(Captcha.class);
      
        Filters filters = new Filters();
        filters.add(new Filter("keyCaptcha", keyCaptcha));
        List<Captcha> keyCaptchas = captchaDAO.search(dataSession, filters, null, null);
        
        if (keyCaptchas.size()>1) {
            throw new RuntimeException("Un keyCaptcha ha sido usado más de una vez (" + keyCaptchas.size() +"):"+keyCaptcha);
        }  
        
        if (keyCaptchas.size()==1) {
            return true;
        } else {
            return false;
        }
      
        
    }
    
    
    private void storeKeyCaptcha(DataSession dataSession, String keyCaptcha) throws BusinessException {
        GenericDAO<Captcha,Integer> captchaDAO=daoFactory.getDAO(Captcha.class);
        
        Captcha captcha=new Captcha();
        captcha.setKeyCaptcha(keyCaptcha);
        captcha.setFecha(new Date());

        captchaDAO.insert(dataSession, captcha);
    }
    
    

    private byte[] getSecretKey() {

        String secureKey=Config.getSetting("app.captcha.secretKey");
        
        if (secureKey==null) {
            throw new RuntimeException("No existe el valor de configuración de app.captcha.secretKey");
        }
        
        return SecureKeyGenerator.convertSecureKeyAsArrayByte(secureKey);


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
