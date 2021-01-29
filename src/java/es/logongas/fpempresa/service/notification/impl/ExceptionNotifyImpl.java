/**
 * FPempresa Copyright (C) 2019 Lorenzo González
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
package es.logongas.fpempresa.service.notification.impl;

import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.ix3.security.authorization.BusinessSecurityException;
import es.logongas.ix3.web.util.ExceptionNotify;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Notificación de que ha habido una excepción
 * @author logongas
 */
public class ExceptionNotifyImpl implements ExceptionNotify {

    @Autowired
    Notification notification;
    
    @Override
    public void notify(Throwable throwable, HttpServletRequest httpServletRequest) {
        String className=throwable.getClass().getSimpleName();
        
        if (throwable instanceof BusinessSecurityException) {
            notification.exception(className + ":"+ throwable.getLocalizedMessage(), throwable.toString(), null);
        } else {
            notification.exception(className + ":"+ throwable.getLocalizedMessage(), getHttpRequestAsString(httpServletRequest), throwable);
        }
    }
    
    private String getHttpRequestAsString(HttpServletRequest httpServletRequest) {
        StringBuilder sb=new StringBuilder();
        sb.append("\n\tURL=");
        sb.append(httpServletRequest.getRequestURI()); 
        sb.append("\n\tQueryString=");
        sb.append(httpServletRequest.getQueryString()); 
        sb.append("\n\tMethod=");
        sb.append(httpServletRequest.getMethod()); 
        sb.append("\n\tHeaders:");
        Enumeration<String> names=httpServletRequest.getHeaderNames();
        while (names.hasMoreElements()) {
            String name=names.nextElement();
            String value=httpServletRequest.getHeader(name);

            sb.append("\n\t\t");
            sb.append(name);
            sb.append("=");
            sb.append(value);
         
        }

        return sb.toString();
    }    
}
