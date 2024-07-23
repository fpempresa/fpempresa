/*
 * FPempresa Copyright (C) 2024 Lorenzo González
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

import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.web.util.ControllerHelper;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class ControllerUtil {
    
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private Conversion conversion;
    
    public Date getDateParameter(HttpServletRequest httpServletRequest,String parameterName) {
        String value=getParameterValue(httpServletRequest, parameterName);
        if (value==null) {
            return null;
        }
        
        return (Date) conversion.convertFromString(value, Date.class);
    }
    
    public Integer getIntegerParameter(HttpServletRequest httpServletRequest,String parameterName) {
        String value=getParameterValue(httpServletRequest, parameterName);
        if (value==null) {
            return null;
        }
        
        return (Integer)conversion.convertFromString(value, Integer.class);
    } 
    
    public String getStringParameter(HttpServletRequest httpServletRequest,String parameterName) {
        String value=getParameterValue(httpServletRequest, parameterName);
        
        return value;
    }     
    
    private String getParameterValue(HttpServletRequest httpServletRequest,String parameterName) {
        String value=controllerHelper.getParameter(httpServletRequest, parameterName);
        if (value==null) {
            return null;
        }
        if (value.trim().equals("")) {
            return null;
        }  
        
        return value;
    }
    
}
