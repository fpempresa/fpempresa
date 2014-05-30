/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.logongas.fpempresa.modelo.centro;

import es.logongas.fpempresa.modelo.comun.Direccion;
import es.logongas.ix3.core.annotations.Caption;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Centros de FP
 * @author Lorenzo
 */
public class Centro {
    private int idCentro;
    
    @NotBlank
    private String  descripcion;
    
    @NotNull
    @Valid
    private Direccion direccion;
    
    @Caption("Pertenece a FPempresa")
    private boolean fpempresa;

    public Centro() {
    }

    @Override
    public String toString() {
        return descripcion;
    }

    
    
    /**
     * @return the idCentro
     */
    public int getIdCentro() {
        return idCentro;
    }

    /**
     * @param idCentro the idCentro to set
     */
    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
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

    /**
     * @return the direccion
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the fpempresa
     */
    public boolean isFpempresa() {
        return fpempresa;
    }

    /**
     * @param fpempresa the fpempresa to set
     */
    public void setFpempresa(boolean fpempresa) {
        this.fpempresa = fpempresa;
    }



}
