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
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import static es.logongas.fpempresa.presentacion.api.AdministradorTest.cookies;
import es.logongas.ix3.util.ReflectionUtil;
import java.util.HashMap;
import java.util.Map;
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
public abstract class GenericTestCRUD {

    static private String apiBase;

    static {
        apiBase = "http://localhost:8084/fpempresa";
        RestAssured.registerParser("text",Parser.TEXT);
    }

    static public String getApiBase() {
        return apiBase;
    }
    
    static public void testSchema(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }
        
        ValidatableResponse validatableResponse
                = given().
                cookies(cookies).
                param("$expand", "direccion.municipio,direccion.municipio.provincia").
                when().
                get(apiBase + "/api/administrador/" + entityClass.getSimpleName() + "/$schema").
                then();

        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("$className", equalTo("Schema")).
                    body("className", equalTo(entityClass.getSimpleName()));
        }
    }

    static public void testPaginatedSearch(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }        
        
        ValidatableResponse validatableResponse
                = given().
                cookies(cookies).
                param("$orderby", "").
                param("$pagenumber", "0").
                param("$pagesize", "20").
                when().
                get(apiBase + "/api/administrador/" + entityClass.getSimpleName() + "").
                then();

        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("content", not(nullValue())).
                    body("pageNumber", equalTo(0)).
                    body("pageSize", equalTo(20)).
                    body("content", not(nullValue()));
        }
    }
    static public void testSearch(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }        
        
        ValidatableResponse validatableResponse
                = given().
                cookies(cookies).
                when().
                get(apiBase + "/api/administrador/" + entityClass.getSimpleName() + "").
                then();

        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("",hasSize(greaterThan(-1)));
        }
    }    
    

    static public void testCreate(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }        
        
        ValidatableResponse validatableResponse
                = given().
                cookies(cookies).
                when().
                get(apiBase + "/api/administrador/" + entityClass.getSimpleName() + "/$create").
                then();

        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName()));
        }
    }

    static public void testRead(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies, int id) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }
        
        ValidatableResponse validatableResponse
                = given().
                cookies(cookies).
                when().
                get(apiBase + "/api/administrador/" + entityClass.getSimpleName() + "/" + id).
                then();

        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName()));
        }
    }

    static public int testInsert(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies, Object entity,String primaryKeyName) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }
        
        Response response= given().
                contentType("application/json").
                cookies(cookies).
                body(entity).
                when().
                post(apiBase + "/api/administrador/" + entityClass.getSimpleName());
        
        
        ValidatableResponse validatableResponse=response
                .then();

        System.out.println(response.body().print());
        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_CREATED);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName()));
            Integer id=((Integer)response.path(primaryKeyName));
            ReflectionUtil.setValueToBean(entity, primaryKeyName,id.intValue());
            return id;
        } else {
            return 0;
        }
        
        
    }

    static public void testUpdate(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies, Object entity,String primaryKeyName) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }
        
        Response response= given().
                contentType("application/json").
                cookies(cookies).
                body(entity).
                when().
                put(apiBase + "/api/administrador/" + entityClass.getSimpleName()+"/"+ReflectionUtil.getValueFromBean(entity, primaryKeyName));
        ValidatableResponse validatableResponse=response
                .then();

        System.out.println(response.body().print());
        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_OK);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

        if (success==true) {
            validatableResponse.
                    body("$className", equalTo(entityClass.getSimpleName())).
                    body(primaryKeyName,equalTo(ReflectionUtil.getValueFromBean(entity, primaryKeyName)));
        }
    }    
    
    static public void testDelete(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies,int id) {
        if (cookies==null) {
            cookies=new HashMap<String, String>();
        }
        
        ValidatableResponse validatableResponse
                = given().
                cookies(cookies).
                when().
                delete(apiBase + "/api/administrador/" + entityClass.getSimpleName() + "/" + id).
                then();

        if (success==true) {
            validatableResponse.statusCode(HttpStatus.SC_NO_CONTENT);
        } else {
            validatableResponse.statusCode(httpStatus);
        }

    }   
    
    static public void testCRUD(Class entityClass,boolean success, int httpStatus, Map<String, String> cookies, Object entity,String primaryKeyName) {
        testSchema(entityClass, success, httpStatus,cookies);
        testPaginatedSearch(entityClass, success, httpStatus,cookies);
        testCreate(entityClass, success, httpStatus,cookies);
        
        int id = testInsert(entityClass, success, httpStatus,cookies, entity, primaryKeyName);
        if (success==true) {
            testRead(entityClass, false, HttpStatus.SC_NO_CONTENT, cookies, 0);
        } else {
            testRead(entityClass, false, httpStatus, cookies, 0);
        }
        testRead(entityClass, success, httpStatus,cookies, id);
        ReflectionUtil.setValueToBean(entity, primaryKeyName, id);
        testUpdate(entityClass, success, httpStatus,cookies, entity, primaryKeyName);
        testSearch(entityClass, success, httpStatus,cookies);
        testDelete(entityClass, success, httpStatus, cookies, id);        
    }
    
}
