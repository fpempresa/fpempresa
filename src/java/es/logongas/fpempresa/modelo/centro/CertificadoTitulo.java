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

import es.logongas.fpempresa.modelo.titulado.TipoDocumento;

/**
 *
 * @author logongas
 */
public class CertificadoTitulo {
    
    private TipoDocumento tipoDocumento;
    private String nif;
    private String nombre;
    private String apellidos;
    private boolean certificadoTitulo;
    private long idFormacionAcademica;


    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param nif the nif to set
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the certificadoTitulo
     */
    public boolean isCertificadoTitulo() {
        return certificadoTitulo;
    }

    /**
     * @param certificadoTitulo the certificadoTitulo to set
     */
    public void setCertificadoTitulo(boolean certificadoTitulo) {
        this.certificadoTitulo = certificadoTitulo;
    }

    /**
     * @return the tipoDocumento
     */
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the idFormacionAcademica
     */
    public long getIdFormacionAcademica() {
        return idFormacionAcademica;
    }

    /**
     * @param idFormacionAcademica the idFormacionAcademica to set
     */
    public void setIdFormacionAcademica(long idFormacionAcademica) {
        this.idFormacionAcademica = idFormacionAcademica;
    }
    
}
