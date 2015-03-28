/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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

import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import java.util.Date;
import java.util.List;

/**
 * Oferta de una empresa
 * @author logongas
 */
public class Oferta {
    
    private int idOferta;
    
    private Empresa empresa;
    
    private String puesto;
    
    private String descripcion;
    
    private List<Ciclo> ciclos;
    
    private EstadoOferta estadoOferta;
    
    private Date fecha;
    
    private Municipio municipio;
    
    private TipoOferta tipoOferta;

    /**
     * @return the idOferta
     */
    public int getIdOferta() {
        return idOferta;
    }

    /**
     * @param idOferta the idOferta to set
     */
    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
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
     * @return the ciclos
     */
    public List<Ciclo> getCiclos() {
        return ciclos;
    }

    /**
     * @param ciclos the ciclos to set
     */
    public void setCiclos(List<Ciclo> ciclos) {
        this.ciclos = ciclos;
    }

    /**
     * @return the estadoOferta
     */
    public EstadoOferta getEstadoOferta() {
        return estadoOferta;
    }

    /**
     * @param estadoOferta the estadoOferta to set
     */
    public void setEstadoOferta(EstadoOferta estadoOferta) {
        this.estadoOferta = estadoOferta;
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

    /**
     * @return the tipoOferta
     */
    public TipoOferta getTipoOferta() {
        return tipoOferta;
    }

    /**
     * @param tipoOferta the tipoOferta to set
     */
    public void setTipoOferta(TipoOferta tipoOferta) {
        this.tipoOferta = tipoOferta;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
