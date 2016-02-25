/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.api;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.educacion.Grado;
import es.logongas.fpempresa.modelo.educacion.LeyEducativa;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.presentacion.controller.UsuarioRESTController;
import es.logongas.fpempresa.service.populate.GeneradorDatosAleatorios;
import static es.logongas.fpempresa.service.populate.GeneradorDatosAleatorios.getMunicipioAleatorio;
import es.logongas.ix3.util.ReflectionUtil;
import es.logongas.ix3.web.util.MimeType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 *
 * @author logongas
 */
public abstract class TestUtil {

    static {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8084;
        RestAssured.basePath = "/fpempresa";
    }

    static public Map<String, String> login(String login, String password) {
        Response response = given().log().ifValidationFails().contentType("application/json").accept("application/json").queryParam("login", login).queryParam("password", password).when().post("/api/session");
        Map<String, String> cookies = response.getCookies();
        response.then().log().ifValidationFails().statusCode(HttpStatus.SC_OK);

        return cookies;
    }

    static public void testSchema(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }

        ValidatableResponse validatableResponse
                = given().log().ifValidationFails().
                cookies(cookies).
                param("$expand", "direccion.municipio,direccion.municipio.provincia").
                when().
                get("/api/" + appPath + "/" + entityClass.getSimpleName() + "/$schema").
                then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("$className", equalTo("Schema")).
                    body("className", equalTo(entityClass.getSimpleName()));
        }
    }

    static public Response testPaginatedSearch(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies, Map<String, Object> params) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        
                Response response= given().log().ifValidationFails().
                queryParams(params).
                cookies(cookies).
                param("$orderby", "").
                param("$pagenumber", "0").
                param("$pagesize", "20").when().
                get("/api/" + appPath + "/" + entityClass.getSimpleName() + "");
                ValidatableResponse validatableResponse=response. 
                then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("content", not(nullValue())).
                    body("pageNumber", equalTo(0)).
                    body("pageSize", equalTo(20)).
                    body("content", not(nullValue()));
        }
        
        return response;
    }

    static public Response testSearch(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies, Map<String, Object> params) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        Response response=given().log().ifValidationFails().
        queryParams(params).
        cookies(cookies).
        when().
        get("/api/" + appPath + "/" + entityClass.getSimpleName() + "");

        ValidatableResponse validatableResponse = response.then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("", hasSize(greaterThan(-1)));
        }
        
        return response;
    }

    static public void testCreate(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }

        ValidatableResponse validatableResponse
                = given().log().ifValidationFails().
                cookies(cookies).
                when().
                get("/api/" + appPath + "/" + entityClass.getSimpleName() + "/$create").
                then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName()));
        }
    }

    static public ValidatableResponse testRead(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies, int id, Map<String, Object> params) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        ValidatableResponse validatableResponse
                = given().log().ifValidationFails().
                queryParams(params).
                cookies(cookies).
                when().
                get("/api/" + appPath + "/" + entityClass.getSimpleName() + "/" + id).
                then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName()));
        }
        
        return validatableResponse;
    }

    static public int testInsert(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies, Object entity, String primaryKeyName,Map<String, Object> params) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        Response response = given().log().ifValidationFails().
                queryParams(params).
                contentType("application/json").
                cookies(cookies).
                body(entity).
                when().
                post("/api/" + appPath + "/" + entityClass.getSimpleName());

        ValidatableResponse validatableResponse = response
                .then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_CREATED);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName()));
            Integer id = ((Integer) response.path(primaryKeyName));
            ReflectionUtil.setValueToBean(entity, primaryKeyName, id.intValue());
            return id;
        } else {
            return 0;
        }

    }

    static public void testUpdate(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies, Object entity, String primaryKeyName, Map<String, Object> params) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        Response response = given().log().ifValidationFails().
                queryParams(params).
                contentType("application/json").
                cookies(cookies).
                body(entity).
                when().
                put("/api/" + appPath + "/" + entityClass.getSimpleName() + "/" + ReflectionUtil.getValueFromBean(entity, primaryKeyName));
        ValidatableResponse validatableResponse = response
                .then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success == true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName())).
                    body(primaryKeyName, equalTo(ReflectionUtil.getValueFromBean(entity, primaryKeyName)));
        }
    }

    static public void testDelete(String appPath, Class entityClass, boolean success, int httpStatus, Map<String, String> cookies, int id) {
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }

        ValidatableResponse validatableResponse
                = given().log().ifValidationFails().
                cookies(cookies).
                when().
                delete("/api/" + appPath + "/" + entityClass.getSimpleName() + "/" + id).
                then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_NO_CONTENT);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

    }

    static public void testCRUD(CRUDTestConfiguration crudTestConfiguration) {
        String appPath = crudTestConfiguration.appPath;
        Class entityClass = crudTestConfiguration.entityClass;
        boolean success = crudTestConfiguration.success;
        int httpStatus = crudTestConfiguration.httpStatus;
        Map<String, String> cookies = crudTestConfiguration.cookies;
        Object entity = crudTestConfiguration.entity;
        String primaryKeyName = crudTestConfiguration.primaryKeyName;
        boolean paginated = crudTestConfiguration.paginated;
        Map<String, Object> params = crudTestConfiguration.params;
        boolean readNoContent = crudTestConfiguration.readNoContent;

        testSchema(appPath, entityClass, success, httpStatus, cookies);
        if (paginated) {
            testPaginatedSearch(appPath, entityClass, success, httpStatus, cookies, params);
        } else {
            testSearch(appPath, entityClass, success, httpStatus, cookies, params);
        }
        testCreate(appPath, entityClass, success, httpStatus, cookies);

        int id = testInsert(appPath, entityClass, success, httpStatus, cookies, entity, primaryKeyName,params);
        if (readNoContent==true) {
            if (success == true) {
                testRead(appPath, entityClass, false, HttpStatus.SC_NO_CONTENT, cookies, 0,params);
            } else {
                testRead(appPath, entityClass, false, httpStatus, cookies, 0,params);
            }
        }
        testRead(appPath, entityClass, success, httpStatus, cookies, id,params);
        ReflectionUtil.setValueToBean(entity, primaryKeyName, id);
        testUpdate(appPath, entityClass, success, httpStatus, cookies, entity, primaryKeyName,params);
        if (paginated) {
            testPaginatedSearch(appPath, entityClass, success, httpStatus, cookies, params);
        } else {
            testSearch(appPath, entityClass, success, httpStatus, cookies, params);
        }
        testDelete(appPath, entityClass, success, httpStatus, cookies, id);
    }

    
    static public void  testUpdatePassword(String appPath, boolean success, int httpStatus, Map<String, String> cookies,int idIdentity,String currentPassword) {

        
        if (cookies == null) {
            cookies = new HashMap<String, String>();
        }

        ValidatableResponse validatableResponse
                = given().log().ifValidationFails().
                cookies(cookies).
                body(new UsuarioRESTController.ChangePassword(currentPassword, currentPassword)).
                when().
                patch("/api/" + appPath + "/Usuario/{id}/updatePassword", idIdentity).
                then().log().ifValidationFails();

        if (success == true) {
            validatableResponse.statusCode(HttpStatus.SC_NO_CONTENT);
        } else {
            validatableResponse.statusCode(httpStatus);
        }        


    }    
    
    
    static final public Oferta createOfertaAplicacionesWeb(Empresa empresa)  {
        Oferta oferta = new Oferta();

        if (empresa == null) {
            empresa = GeneradorDatosAleatorios.createEmpresaAleatoria(null);
        }

        String puesto = GeneradorDatosAleatorios.getNombrePuestoTrabajo();

        oferta.setEmpresa(empresa);
        oferta.setCerrada(false);
        Familia familia=new Familia();
        familia.setIdFamilia(14);
        oferta.setFamilia(familia);
        Set<Ciclo> ciclos = new HashSet<Ciclo>();
        Ciclo ciclo=new Ciclo();
        ciclo.setFamilia(familia);
        ciclo.setIdCiclo(196);
        
//        ciclo.setDescripcion("Desarrollo de Aplicaciones Web");
//        LeyEducativa leyEducativa=new LeyEducativa();
//        leyEducativa.setIdLeyEducativa(2);
//        ciclo.setLeyEducativa(leyEducativa);
//        Grado grado=new Grado();
//        grado.setIdGrado(3);
//        ciclo.setGrado(grado);
        
        
        ciclos.add(ciclo);
        oferta.setCiclos(ciclos);

        Contacto contacto = new Contacto();
        if (empresa.getCentro() != null) {
            contacto.setTextoLibre("Enviar un correo a 'rrrhh@" + empresa.getNombreComercial().replaceAll("\\s+", "").toLowerCase() + ".com' con el asunto 'oferta trabajo de " + puesto + "'.\nIncluir curriculum en formato PDF");
        }
        oferta.setContacto(contacto);
        oferta.setDescripcion("Oferta laboral para trabajar como '" + puesto + "' a jornada completa. \nNo es necesaria experiencia.");
        oferta.setMunicipio(getMunicipioAleatorio());
        oferta.setPuesto(puesto);

        return oferta;
    }
    
}
