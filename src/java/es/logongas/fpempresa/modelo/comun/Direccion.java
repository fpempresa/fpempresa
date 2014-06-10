/**
 *   FPempresa
 *   Copyright (C) 2014  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.logongas.fpempresa.modelo.comun;

import es.logongas.ix3.core.annotations.Caption;
import es.logongas.ix3.core.annotations.ValuesList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Direccion {
    
    @NotNull
    @Caption("Tipo de vía")
    private TipoVia tipoVia;
    
    @NotBlank
    @Caption("Nombre de la vía")
    private String nombreVia;
    
    @NotBlank
    @Caption("Patio,Puerta, Nº, Bloque, etc.")
    private String otrosDireccion;
    
    @NotBlank
    @Caption("Código postal")
    @Pattern(regexp = "\\d{5}| {0}")
    private String codigoPostal;
    
    @NotNull
    @ValuesList(shortLength=true)
    private Provincia provincia;
    
    @NotNull
    @ValuesList(shortLength=true)
    private Municipio municipio;

    /**
     * @return the tipoVia
     */
    public TipoVia getTipoVia() {
        return tipoVia;
    }

    /**
     * @param tipoVia the tipoVia to set
     */
    public void setTipoVia(TipoVia tipoVia) {
        this.tipoVia = tipoVia;
    }

    /**
     * @return the nombreVia
     */
    public String getNombreVia() {
        return nombreVia;
    }

    /**
     * @param nombreVia the nombreVia to set
     */
    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    /**
     * @return the otrosDireccion
     */
    public String getOtrosDireccion() {
        return otrosDireccion;
    }

    /**
     * @param otrosDireccion the otrosDireccion to set
     */
    public void setOtrosDireccion(String otrosDireccion) {
        this.otrosDireccion = otrosDireccion;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the provincia
     */
    public Provincia getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the municipio
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}
