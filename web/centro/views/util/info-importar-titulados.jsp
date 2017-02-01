<%-- 
    Document   : info-importar-titulados
    Created on : Dec 21, 2016, 3:16:53 PM
    Author     : ruben
--%>
<%@page import="es.logongas.fpempresa.service.educacion.ciclo.CicloCRUDService"%>
<%@page import="es.logongas.fpempresa.modelo.educacion.Ciclo"%>
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
<a href="../centro/views/util/titulados.json">Ver ejemplo de archivo</a>
<br>
<div class="row">
    <div class="col-xs-12">
        <h4>Idiomas (idioma)</h4>
    </div>
</div>
<div class="row form__container" style="font-size: 10px">
    <div class="col-xs-3">INGLES</div>
    <div class="col-xs-3">FRANCES</div>
    <div class="col-xs-3">ALEMAN</div>
    <div class="col-xs-3">OTRO</div>
</div>

<br>
<div class="row">
    <div class="col-xs-12">
        <h4>Nivel Idiomas (nivelIdioma)</h4>.
    </div>
</div>
<div class="row form__container" style="font-size: 10px">
    <div class="col-xs-2">A1</div>
    <div class="col-xs-2">A2</div>
    <div class="col-xs-2">B1</div>
    <div class="col-xs-2">B2</div>
    <div class="col-xs-2">C1</div>
    <div class="col-xs-2">C2</div>
</div>

<br>
<div class="row">
    <div class="col-xs-12">
        <h4>Tipo Documento (tipoDocumento)</h4>
    </div>
</div>
<div class="row form__container" style="font-size: 10px">
    <div class="col-xs-4">NIF_NIE</div>
    <div class="col-xs-4">PASAPORTE</div>
    <div class="col-xs-4">OTRO</div>
</div>

<br>
<div class="row">
    <div class="col-xs-12">
        <h4>Formación Académica (tipoFormacionAcademica)</h4>
    </div>
</div>
<div class="row form__container" style="font-size: 10px">
    <div class="col-xs-4">CICLO_FORMATIVO</div>
    <div class="col-xs-4">TITULO_UNIVERSITARIO</div>
    <div class="col-xs-4">OTRO_TITULO</div>
</div>
<div class="row">
    <div class="col-xs-12">
        <h4>Códigos de ciclo (idCiclo)</h4>
    </div>
</div>
   <div class="row form__container">
      <%
        String message = "";
        DataSession dataSession = null;
        try {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            DataSessionFactory dataSessionFactory = webApplicationContext.getBean(DataSessionFactory.class);
            dataSession = dataSessionFactory.getDataSession();
            CRUDServiceFactory serviceFactory = webApplicationContext.getBean(CRUDServiceFactory.class);
            CicloCRUDService cicloCRUDService = (CicloCRUDService) serviceFactory.getService(Ciclo.class);
            List<Ciclo> listaCiclos = cicloCRUDService.search(dataSession, null, null, null);
            for (int i = 1; i < listaCiclos.size(); i++) {
                Integer codigoDeCiclo = listaCiclos.get(i).getIdCiclo();
                String nombreCiclo = listaCiclos.get(i).getDescripcion();
    %>
    <div class="col-xs-4">
            <div class="col-xs-4">
                <strong><%=codigoDeCiclo%></strong>
            </div>
            <div class="col-xs-8">
                <%=nombreCiclo%>
            </div>
    </div>
    <%}%>
</div>
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


<br>
<div class="row">
    <div class="col-xs-12">
        <h4>Códigos de Centro (idCentro)</h4>
    </div>
</div>
<div class="row form__container" style="font-size: 10px">
    <%
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

<br>
<div class="row">
    <div class="col-xs-12">
        <h4>Códigos de Municipio (idMunicipio)</h4>
    </div>
</div>
<div class="row form__container" style="font-size: 12px; height: 400px; overflow-x: hidden; overflow-y: scroll">
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
