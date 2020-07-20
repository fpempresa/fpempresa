<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="es.logongas.ix3.service.CRUDServiceFactory"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="es.logongas.ix3.dao.DataSession"%>
<%@page import="es.logongas.ix3.dao.DataSessionFactory"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="es.logongas.fpempresa.modelo.comun.usuario.Usuario"%>
<%@page import="es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%
    String message = "";
    if (request.getParameter("token") != null) {
        DataSession dataSession = null;
        try {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            DataSessionFactory dataSessionFactory = webApplicationContext.getBean(DataSessionFactory.class);
            dataSession = dataSessionFactory.getDataSession();
            CRUDServiceFactory serviceFactory = webApplicationContext.getBean(CRUDServiceFactory.class);
            UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
            if (usuarioCRUDService.validarEmail(dataSession, request.getParameter("token"))) {
                message = "Tu correo electrónico se ha validado correctamente";
            } else {
                message = "El token proporcionado es inválido";
            }

        } catch (Exception ex) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            message = "throw new Error('Fallo al cargar el usuario desde el servidor:" + ex.toString() + "');\n/**" + stringWriter.toString() + "**/\n";
        } finally {
            if (dataSession != null) {
                dataSession.close();
            }
        }
    } else {
        message = "No se ha proporcionado ningún token de validación";
    }
%>
<!--
FPempresa
Copyright (C) 2020  Lorenzo González

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see http://www.gnu.org/licenses/
-->

<section  class="c-section"  >
    <div class="c-section__content c-section__content--size-alt  g--padding-top-14 g--margin-bottom-10 g--center" >
        
        <h1 class="c-title  c-title--left g--margin-bottom-7 " style="width: 100%; max-width:500px">
                <span class="c-title__pre">Vamos a validar</span>
                Tu dirección de correo
        </h1>
        

        <p class="c-text c-text--size-alt g--padding-top-7"><%=message%></p>


    </div>
</section>
