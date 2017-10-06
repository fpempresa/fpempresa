/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.modelo.estadisticas;

import es.logongas.fpempresa.modelo.educacion.Ciclo;

/**
 *
 * @author ruben
 */
public class CicloEstadistica extends Ciclo {
        private int valor;

    public CicloEstadistica(int idCiclo, String descripcion, int valor) {
        super(idCiclo, descripcion);
        this.valor = valor;
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

}
