<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="es.logongas.fpempresa.config.Config"%>
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
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");  
%>
<%
    
    String appCorreoSoporte;
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
        expands.add("empresa.direccion.municipio.provincia");
        expands.add("centro.direccion.municipio.provincia");
        expands.add("titulado.direccion.municipio.provincia");
        BeanMapper beanMapper=new BeanMapper(Usuario.class, "foto,claveValidacionEmail,password,acl,memberOf,validadoEmail>,tipoUsuario>,fechaEnvioCorreoAvisoBorrarUsuario,lockedUntil,numFailedLogins,aceptarEnvioCorreos,claveResetearContrasenya,fechaClaveResetearContrasenya", "titulado.formacionesAcademicas");
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
        
        appCorreoSoporte=Config.getSetting("app.correoSoporte");
        
        error="";
    } catch (Exception ex) {
        jsonUser = "null";
        appCorreoSoporte="null";

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


function getServerConfig() {

    return {
        "app.correoSoporte":"<%=appCorreoSoporte%>"
    }

}

var serverLocale="<%=Locale.getDefault() %>";
<%
    Locale[] locales = SimpleDateFormat.getAvailableLocales();
    for(Locale locale : locales) {
        out.println("//" + locale.getDisplayName()+"--"+locale.getLanguage()+"_"+locale.getCountry());
    }    
%>