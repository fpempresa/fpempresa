/**
 * FPempresa Copyright (C) 2014 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.modelo.estadisticas;

import java.util.List;

/**
 *
 * @author logongas
 */
public class Estadisticas {

    private final List<FamiliaEstadistica> titulosFPPorFamilia;
    private final List<FamiliaEstadistica> ofertasPorFamilia;
    private final List<FamiliaEstadistica> candidatosPorFamilia;

    private final int numeroTitulosFP;
    private final int numeroOfertas;
    private final int numeroCentros;
    private final int numeroCandidatos;
    private final int numeroEmpresas;

    public Estadisticas(List<FamiliaEstadistica> titulosFPPorFamilia, List<FamiliaEstadistica> ofertasPorFamilia, List<FamiliaEstadistica> candidatosPorFamilia) {
        this.titulosFPPorFamilia = titulosFPPorFamilia;
        this.ofertasPorFamilia = ofertasPorFamilia;
        this.candidatosPorFamilia = candidatosPorFamilia;

        this.numeroTitulosFP = getSum(this.titulosFPPorFamilia);
        this.numeroOfertas = getSum(this.ofertasPorFamilia);
        this.numeroCandidatos = getSum(this.candidatosPorFamilia);
        this.numeroCentros = 0;
        this.numeroEmpresas = 0;
    }

    public Estadisticas(List<FamiliaEstadistica> titulosFPPorFamilia, List<FamiliaEstadistica> ofertasPorFamilia, List<FamiliaEstadistica> candidatosPorFamilia, Integer numeroCentros, Integer numeroEmpresas) {
        this.titulosFPPorFamilia = titulosFPPorFamilia;
        this.ofertasPorFamilia = ofertasPorFamilia;
        this.candidatosPorFamilia = candidatosPorFamilia;

        this.numeroTitulosFP = getSum(this.titulosFPPorFamilia);
        this.numeroOfertas = getSum(this.ofertasPorFamilia);
        this.numeroCandidatos = getSum(this.candidatosPorFamilia);
        this.numeroCentros = numeroCentros;
        this.numeroEmpresas = numeroEmpresas;
    }

    public Estadisticas(Integer numeroEmpresas, List<FamiliaEstadistica> ofertasPorFamilia) {
        this.titulosFPPorFamilia = null;
        this.ofertasPorFamilia = ofertasPorFamilia;
        this.candidatosPorFamilia = null;

        this.numeroTitulosFP = 0;
        this.numeroOfertas = getSum(this.ofertasPorFamilia);
        this.numeroCandidatos = 0;
        this.numeroCentros = 0;
        this.numeroEmpresas = numeroEmpresas;
    }

    public Estadisticas(List<FamiliaEstadistica> titulosFPPorFamilia, List<FamiliaEstadistica> ofertasPorFamilia, List<FamiliaEstadistica> candidatosPorFamilia, Integer numeroCentros) {
        this.titulosFPPorFamilia = titulosFPPorFamilia;
        this.ofertasPorFamilia = ofertasPorFamilia;
        this.candidatosPorFamilia = candidatosPorFamilia;

        this.numeroTitulosFP = getSum(this.titulosFPPorFamilia);
        this.numeroOfertas = getSum(this.ofertasPorFamilia);
        this.numeroCandidatos = getSum(this.candidatosPorFamilia);
        this.numeroCentros = numeroCentros;
        this.numeroEmpresas = 0;
    }

    private int getSum(List<FamiliaEstadistica> familiasEstadistica) {
        int sum = 0;

        if (familiasEstadistica != null) {
            for (FamiliaEstadistica familiaEstadistica : familiasEstadistica) {
                sum = sum + familiaEstadistica.getValor();
            }
        }

        return sum;
    }

    /**
     * @return the titulosFPPorFamilia
     */
    public List<FamiliaEstadistica> getTitulosFPPorFamilia() {
        return titulosFPPorFamilia;
    }

    /**
     * @return the ofertasPorFamilia
     */
    public List<FamiliaEstadistica> getOfertasPorFamilia() {
        return ofertasPorFamilia;
    }

    /**
     * @return the candidatosPorFamilia
     */
    public List<FamiliaEstadistica> getCandidatosPorFamilia() {
        return candidatosPorFamilia;
    }

    /**
     * @return the numeroTitulosFP
     */
    public int getNumeroTitulosFP() {
        return numeroTitulosFP;
    }

    public int getNumeroEmpresas() {
        return numeroEmpresas;
    }

    /**
     * @return the numeroOfertas
     */
    public int getNumeroOfertas() {
        return numeroOfertas;
    }

    /**
     * @return the numeroCentros
     */
    public int getNumeroCentros() {
        return numeroCentros;
    }

    /**
     * @return the numeroCandidatos
     */
    public int getNumeroCandidatos() {
        return numeroCandidatos;
    }

}
