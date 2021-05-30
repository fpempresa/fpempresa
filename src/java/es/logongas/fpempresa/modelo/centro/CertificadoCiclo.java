/*
 * FPempresa Copyright (C) 2021 Lorenzo González
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
package es.logongas.fpempresa.modelo.centro;


/**
 *
 * @author logongas
 */
public class CertificadoCiclo {
    
    private long idCiclo; 
    private long idFamilia; 
    private String nombreCiclo; 
    private long numTitulosTotales; 
    private long numTitulosCertificados;

    /**
     * @return the nombreCiclo
     */
    public String getNombreCiclo() {
        return nombreCiclo;
    }

    /**
     * @param ciclo the nombreCiclo to set
     */
    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }

    /**
     * @return the numTitulosTotales
     */
    public long getNumTitulosTotales() {
        return numTitulosTotales;
    }

    /**
     * @param numTitulosTotales the numTitulosTotales to set
     */
    public void setNumTitulosTotales(long numTitulosTotales) {
        this.numTitulosTotales = numTitulosTotales;
    }

    /**
     * @return the numTitulosCertificados
     */
    public long getNumTitulosCertificados() {
        return numTitulosCertificados;
    }

    /**
     * @param numTitulosCertificados the numTitulosCertificados to set
     */
    public void setNumTitulosCertificados(long numTitulosCertificados) {
        this.numTitulosCertificados = numTitulosCertificados;
    }

    /**
     * @return the idCiclo
     */
    public long getIdCiclo() {
        return idCiclo;
    }

    /**
     * @param idCiclo the idCiclo to set
     */
    public void setIdCiclo(long idCiclo) {
        this.idCiclo = idCiclo;
    }

    /**
     * @return the idFamilia
     */
    public long getIdFamilia() {
        return idFamilia;
    }

    /**
     * @param idFamilia the idFamilia to set
     */
    public void setIdFamilia(long idFamilia) {
        this.idFamilia = idFamilia;
    }
    
}
