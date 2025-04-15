/*
 * FPempresa Copyright (C) 2025 Lorenzo González
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
package es.logongas.fpempresa.presentacion.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author logongas
 */
public class FilterImplCanonicalRedirect implements Filter {

    private final Map<String,String> redirects=new HashMap<>();
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirects.put("pruebas2.empleafp.com","pruebas.empleafp.com");
        redirects.put("pruebas.empleafp.es","pruebas.empleafp.com"); 
        redirects.put("pruebas.empleafp.info","pruebas.empleafp.com");   
        redirects.put("pruebas.empleafp.net","pruebas.empleafp.com");   
        redirects.put("pruebas.empleafp.org","pruebas.empleafp.com");   
        redirects.put("pruebas.empleafp.eu","pruebas.empleafp.com");   
        redirects.put("pruebas.empleafp.info","pruebas.empleafp.com");   
        redirects.put("pruebas.empleosfp.es","pruebas.empleafp.com");   
        redirects.put("pruebas.empleosfp.com","pruebas.empleafp.com");   
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        
        
        String serverName = httpServletRequest.getServerName();
        
        if (isRequiredRedirect(serverName)) {
            String target = getCanonicalServerName(serverName) + httpServletRequest.getRequestURI();
            if (httpServletRequest.getQueryString() != null) {
                target += "?" + httpServletRequest.getQueryString();
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            httpServletResponse.setHeader("Location", target);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        
    }
    
    
    private boolean isRequiredRedirect(String serverName) {
        if (redirects.get(serverName)==null) {
            return false;
        } else {
            return true;
        }
    }
    
    private String getCanonicalServerName(String serverName) {
        String canonicalServerName;
        
        canonicalServerName=redirects.get(serverName);
        
        if (canonicalServerName==null) {
            throw new RuntimeException("No existe la redirección para " + serverName);
        }
        
        return canonicalServerName;
    }    
    
}

