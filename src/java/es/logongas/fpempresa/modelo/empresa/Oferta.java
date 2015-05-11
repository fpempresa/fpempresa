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
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.ix3.core.annotations.ValuesList;
import es.logongas.ix3.service.rules.ActionRule;
import es.logongas.ix3.service.rules.ConstraintRule;
import es.logongas.ix3.service.rules.RuleContext;
import es.logongas.ix3.service.rules.RuleGroupPredefined;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Oferta de una empresa
 *
 * @author logongas
 */
public class Oferta {

    private int idOferta;

    private Date fecha;

    @NotNull
    private Empresa empresa;

    @NotEmpty
    private String puesto;

    @NotEmpty
    private String descripcion;

    @NotNull
    private Municipio municipio;

    @NotNull
    @ValuesList(shortLength = true)
    private Familia familia;

    private Set<Ciclo> ciclos;

    @NotNull
    private TipoOferta tipoOferta;

    private boolean cerrada;
   
    public Oferta() {
        this.tipoOferta = TipoOferta.LABORAL;
    }

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
     * @return the familia
     */
    public Familia getFamilia() {
        return familia;
    }

    /**
     * @param familia the familia to set
     */
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    /**
     * @return the ciclos
     */
    public Set<Ciclo> getCiclos() {
        return ciclos;
    }

    /**
     * @param ciclos the ciclos to set
     */
    public void setCiclos(Set<Ciclo> ciclos) {
        this.ciclos = ciclos;
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
     * @return the cerrada
     */
    public boolean isCerrada() {
        return cerrada;
    }

    /**
     * @param cerrada the cerrada to set
     */
    public void setCerrada(boolean cerrada) {
        this.cerrada = cerrada;
    }

    @ConstraintRule(message = "El tipo de oferta debe ser laboral", groups = RuleGroupPredefined.PostInsertOrUpdate.class)
    private boolean isTipoOfertaLaboral(RuleContext ruleContext) {
        if (this.tipoOferta != TipoOferta.LABORAL) {
            return false;
        }

        return true;
    }

    @ConstraintRule(message = "Es necesario indicar al menos un ciclo formativo", groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private boolean isCicloRequerido(RuleContext ruleContext) {
        if ((this.ciclos == null) || (this.ciclos.isEmpty())) {
            return false;
        }

        return true;
    }

    @ActionRule(groups = RuleGroupPredefined.PreInsert.class)
    private void establecerFechaCreacion() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        this.fecha = date;
    }

}
