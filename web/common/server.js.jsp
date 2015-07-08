<%@ page session="false" %>
<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="es.logongas.ix3.security.util.WebSessionSidStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="es.logongas.ix3.core.hibernate.HibernateUtil"%>
<%@page import="es.logongas.ix3.security.authentication.AuthenticationManager"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="es.logongas.ix3.web.json.JsonWriter"%>
<%@page import="es.logongas.ix3.web.json.JsonFactory"%>
<%@page import="es.logongas.ix3.security.authentication.Principal"%>
<%@page import="org.springframework.beans.factory.config.AutowireCapableBeanFactory"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%
    String jsonUser;
    String error;
    try {
        HibernateUtil.openSessionAndBindToThread();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        JsonFactory jsonFactory = webApplicationContext.getBean(JsonFactory.class);
        AuthenticationManager authenticationManager = webApplicationContext.getBean(AuthenticationManager.class);
        WebSessionSidStorage webSessionSidStorage = webApplicationContext.getBean(WebSessionSidStorage.class);

        Serializable sid = webSessionSidStorage.getSid(request,response);
        Principal principal;
        List<String> expands=new ArrayList<String>();
        expands.add("empresa");
        expands.add("centro");
        expands.add("titulado.direccion.municipio.provincia");
        if (sid == null) {
            principal = null;
        } else {
            principal = authenticationManager.getPrincipalBySID(sid);
        }
        
        if (principal != null) {
            JsonWriter jsonWriter = jsonFactory.getJsonWriter(Principal.class);
            jsonUser = jsonWriter.toJson(principal,expands);
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
         HibernateUtil.closeSessionAndUnbindFromThread();
    }

%>

//El usuario que está loggeado en el servidor
var user=<%=jsonUser%>;
<%=error%>

//El context Path de la aplicación
function getContextPath() {
    return "<%=request.getContextPath()%>";
}