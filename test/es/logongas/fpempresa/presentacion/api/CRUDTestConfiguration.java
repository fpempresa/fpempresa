/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.api;

import java.util.Map;

/**
 *
 * @author logongas
 */
public class CRUDTestConfiguration {
    String appPath;
    Class entityClass;
    boolean success;
    int httpStatus;
    Map<String, String> cookies;
    Object entity;
    String primaryKeyName;
    boolean paginated;
    Map<String, Object> params; 
    boolean readNoContent;
}
