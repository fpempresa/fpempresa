<%-- 
    Document   : info-importar-titulados
    Created on : Dec 21, 2016, 3:16:53 PM
    Author     : ruben
--%>
<%@page import="java.util.List"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="es.logongas.ix3.service.CRUDServiceFactory"%>
<%@page import="es.logongas.ix3.dao.DataSessionFactory"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="es.logongas.ix3.dao.DataSession"%>
<%@page import="es.logongas.fpempresa.service.centro.CentroCRUDService"%>
<%@page import="es.logongas.fpempresa.service.centro.impl.CentroCRUDServiceImpl"%>
<%@page import="es.logongas.fpempresa.modelo.comun.geo.Municipio"%>
<%@page import="es.logongas.fpempresa.modelo.centro.Centro"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<a href="/centro/views/util/titulados.json">Ver ejemplo de archivo</a>
<h4>Códigos de Centro</h4>
<br>
<div class="row form__container" style="font-size: 10px">
    <%
        String message = "";
        DataSession dataSession = null;
        try {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            DataSessionFactory dataSessionFactory = webApplicationContext.getBean(DataSessionFactory.class);
            dataSession = dataSessionFactory.getDataSession();
            CRUDServiceFactory serviceFactory = webApplicationContext.getBean(CRUDServiceFactory.class);
            CentroCRUDService centroCRUDService = (CentroCRUDService) serviceFactory.getService(Centro.class);
            List<Centro> listaCentros = centroCRUDService.search(dataSession, null, null, null);

            for (int i = 1; i < listaCentros.size(); i++) {
                Integer codigoDeCentro = listaCentros.get(i).getIdCentro();
                String nombreCentro = listaCentros.get(i).getNombre();
    %>
    <div class="col-xs-4">
            <div class="col-xs-4">
                <strong><%=codigoDeCentro%></strong>
            </div>
            <div class="col-xs-8">
                <%=nombreCentro%>
            </div>
    </div>
    <%}%>
</div>
<h4>Códigos de Municipio</h4>
<br>
<div class="row" style="font-size: 12px">
    <jsp:include page="codigos-municipio.html"></jsp:include>   
<%
        } catch (Exception ex) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            message = "throw new Error('Fallo al cargar los datos desde el servidor :" + ex.toString() + "');\n/**" + stringWriter.toString() + "**/\n";
        
        } finally {
            if (dataSession != null) {
                dataSession.close();
            }
        }
    %>
</div>
