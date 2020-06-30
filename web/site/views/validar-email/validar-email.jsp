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
                message = "Su correo electrónico se ha verificado correctamente";
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

<section  class="c-section" data-scroll-trigger="true" >
    <div class="c-section__content  g--padding-vertical-15 " >
        <form  name="mainForm" id="mainForm" ix3-form class="c-form g--center" style="width: 100%; max-width:500px" novalidate>
            <h1 class="c-title c-title--left g--margin-bottom-7">
                <span class="c-title__pre">Vamos a validar</span>
                Tu dirección de correo
            </h1>
            
            <p class="c-text c-text--size-alt g--padding-top-7"><%=message%></p>
                                     

        </form>
    </div>
</section>
