<%@ page session="false" %>
<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="es.logongas.ix3.dao.DataSession"%>
<%@page import="es.logongas.ix3.dao.DataSessionFactory"%>
<%@page import="es.logongas.ix3.core.Principal"%>
<%@page import="es.logongas.fpempresa.modelo.comun.usuario.Usuario"%>
<%@page import="es.logongas.ix3.web.json.beanmapper.BeanMapper"%>
<%@page import="es.logongas.ix3.web.json.beanmapper.Expands"%>
<%@page import="es.logongas.ix3.web.security.WebSessionSidStorage"%>
<%@page import="java.util.List"%>
<%@page import="es.logongas.ix3.security.authentication.AuthenticationManager"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="es.logongas.ix3.web.json.JsonWriter"%>
<%@page import="es.logongas.ix3.web.json.JsonFactory"%>
<%@page import="org.springframework.beans.factory.config.AutowireCapableBeanFactory"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%
    String jsonUser;
    String error;
    
    DataSession dataSession=null;
    try {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        JsonFactory jsonFactory = webApplicationContext.getBean(JsonFactory.class);
        AuthenticationManager authenticationManager = webApplicationContext.getBean(AuthenticationManager.class);
        WebSessionSidStorage webSessionSidStorage = webApplicationContext.getBean(WebSessionSidStorage.class);
        DataSessionFactory dataSessionFactory = webApplicationContext.getBean(DataSessionFactory.class);
        dataSession=dataSessionFactory.getDataSession();
        
        Serializable sid = webSessionSidStorage.getSid(request,response);
        Principal principal;
        Expands expands=new Expands();
        expands.add("empresa");
        expands.add("centro");
        expands.add("titulado.direccion.municipio.provincia");
        BeanMapper beanMapper=new BeanMapper(Usuario.class, "foto,claveValidacionEmail,password,acl,memberOf,validadoEmail>,tipoUsuario>", null);
        if (sid == null) {
            principal = null;
        } else {
            principal = authenticationManager.getPrincipalBySID(sid,dataSession);
        }
        
        if (principal != null) {
            JsonWriter jsonWriter = jsonFactory.getJsonWriter(Principal.class);
            jsonUser = jsonWriter.toJson(principal,expands,beanMapper);
        } else {
            jsonUser = "null";
        }
        error="";
    } catch (Exception ex) {
        jsonUser = "null";

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        error = "throw new Error('Fallo al cargar el usuario desde el servidor:" + ex.toString() + "');\n/**" + stringWriter.toString() + "**/\n";
    } finally {
         if (dataSession!=null) {
             dataSession.close();
         }
    }

%>

//El usuario que está loggeado en el servidor
var user=<%=jsonUser%>;
<%=error%>

//El context Path de la aplicación
function getContextPath() {
    return "<%=request.getContextPath()%>";
}