/*
 * FPempresa Copyright (C) 2025 Lorenzo González
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
package es.logongas.fpempresa.modelo.empresa;

import es.logongas.ix3.core.annotations.Label;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author logongas
 */
public class DominioConocido {
    

    private int idDominioConocido;

    @NotBlank
    private String dominio;
    
    @Label("Tipo de dominio conocido")
    @NotNull
    private TipoDominioConocido tipoDominioConocido;

    @Label("Fecha de alta")
    private Date fechaAlta;

    public DominioConocido() {
    }

    /**
     * @return the idDominioConocido
     */
    public int getIdDominioConocido() {
        return idDominioConocido;
    }

    /**
     * @param idDominioConocido the idDominioConocido to set
     */
    public void setIdDominioConocido(int idDominioConocido) {
        this.idDominioConocido = idDominioConocido;
    }

    /**
     * @return the dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * @param dominio the dominio to set
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * @return the tipoDominioConocido
     */
    public TipoDominioConocido getTipoDominioConocido() {
        return tipoDominioConocido;
    }

    /**
     * @param tipoDominioConocido the tipoDominioConocido to set
     */
    public void setTipoDominioConocido(TipoDominioConocido tipoDominioConocido) {
        this.tipoDominioConocido = tipoDominioConocido;
    }

    /**
     * @return the fechaAlta
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * @param fechaAlta the fechaAlta to set
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    
}
