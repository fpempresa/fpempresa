/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.modelo.comun.geo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author logongas
 */
public class ComunidadAutonoma {

    @JsonProperty("idComunidadAutonoma")
    private int idComunidadAutonoma;

    private String descripcion;


    public ComunidadAutonoma() {
    }

    @Override
    public String toString() {
        return descripcion;
    }

    /**
     * @return the idComunidadAutonoma
     */
    public int getIdComunidadAutonoma() {
        return idComunidadAutonoma;
    }

    /**
     * @param idComunidadAutonoma the idComunidadAutonoma to set
     */
    public void setIdComunidadAutonoma(int idComunidadAutonoma) {
        this.idComunidadAutonoma = idComunidadAutonoma;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
