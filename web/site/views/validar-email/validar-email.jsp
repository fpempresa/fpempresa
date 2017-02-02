<%-- 
    Document   : testing
    Created on : 28-sep-2016, 15:46:26
    Author     : GnommoStudios
--%>
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
                message = "Su email se ha verificado correctamente";
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


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<section class="container-fluid form__page" >

    <div class="row"  >    
        <div class="col-xs-12 form--center"  >
            <h3 class="form__main-title" >&nbsp;&nbsp;Validar Mail&nbsp;&nbsp;</h3>
            <br>
            <br>
            <br>
        </div>
    </div>


    <div class="row"  >
        <div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 form__container form--center" >
            <h3><%=message%></h3>
        </div>  
    </div>                
</section>

