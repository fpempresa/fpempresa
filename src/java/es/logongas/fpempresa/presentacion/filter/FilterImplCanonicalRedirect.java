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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Este filtro mira si el servidor no es el canónico y lo redirige al canónico
 * Para elo se usa la variable "VIRTUAL_HOST" que tiene la lista de servidores
 * que soporta Y el canónico es el primero de ellos. La variable "VIRTUAL_HOST"
 * está porque se añade desde Docker al usar "nginxproxy" Así que siempre
 * redirige al primero de ellos
 *
 * @author logongas
 */
public class FilterImplCanonicalRedirect implements Filter {

    String canonicalServerName;
    List<String> redirectServerNames = new ArrayList<>();
    private final static String ENV_NAME_VIRTUAL_HOST = "VIRTUAL_HOST";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        String virtualHosts = System.getProperty(ENV_NAME_VIRTUAL_HOST);

        if ((virtualHosts == null) || (virtualHosts.trim().isEmpty())) {
            virtualHosts = System.getenv(ENV_NAME_VIRTUAL_HOST);
        }


        if ((virtualHosts == null) || (virtualHosts.trim().isEmpty())) {
            canonicalServerName = null;
        } else {
            String[] partes = virtualHosts.split(",");
            for (int i = 0; i < partes.length; i++) {
                if (i == 0) {
                    canonicalServerName = partes[i];
                } else {
                    redirectServerNames.add(partes[i]);
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String serverName = httpServletRequest.getServerName();

        if (isRequiredRedirect(serverName)) {

            String target = getTarget(httpServletRequest, httpServletResponse);
            String html = getHTMLRedirect(target);
            sendHTML(html, httpServletRequest, httpServletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }

    private boolean isRequiredRedirect(String serverName) {
        if (canonicalServerName == null) {
            return false;
        }

        if (redirectServerNames.contains(serverName)) {
            return true;
        }

        return false;
    }

    private String getCanonicalServerName(String serverName) {
        if ((canonicalServerName == null) || (canonicalServerName.trim().isEmpty())) {
            throw new RuntimeException("La variable canonicalServerName está vacía para " + serverName);
        }

        return canonicalServerName;
    }

    private String getTarget(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String scheme = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        int serverPort = httpServletRequest.getServerPort();
        String requestURI = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        
        String canonicalServerName=getCanonicalServerName(serverName);
        
        
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(canonicalServerName);

        if ((scheme.equals("http") && serverPort != 80)
                || (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(requestURI);
        if (queryString != null) {
            url.append("?").append(queryString);
        }

        String target = url.toString();

        return target;
    }

    private String getHTMLRedirect(String target) {        
        String html = "<!DOCTYPE html><html lang='es'><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Redirigiendo...</title><script>window.location.href = '" + target + "';</script></head>" +
        "<body><p>Si no eres redirigido automáticamente, haz clic <a href='" + target + "'>aquí</a>.</p></body></html>";
        return html;
    }

    public void sendHTML(String html, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("text/html; charset=UTF-8");
            httpServletResponse.getWriter().print(html);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
