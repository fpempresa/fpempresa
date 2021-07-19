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

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.rule.ActionRule;
import es.logongas.ix3.rule.RuleGroupPredefined;
import java.util.Date;

/**
 *
 * @author logongas
 */
public class Candidato {

    private int idCandidato;

    private Oferta oferta;

    private Usuario usuario;

    private boolean rechazado;

    private Date fecha;
    
    private boolean borrado;

    /**
     * @return the idCandidato
     */
    public int getIdCandidato() {
        return idCandidato;
    }

    /**
     * @param idCandidato the idCandidato to set
     */
    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    /**
     * @return the oferta
     */
    public Oferta getOferta() {
        return oferta;
    }

    /**
     * @param Oferta the oferta to set
     */
    public void setOferta(Oferta Oferta) {
        this.oferta = Oferta;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the rechazado
     */
    public boolean isRechazado() {
        return rechazado;
    }

    /**
     * @param rechazado the rechazado to set
     */
    public void setRechazado(boolean rechazado) {
        this.rechazado = rechazado;
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

    @ActionRule(groups = RuleGroupPredefined.PreInsert.class)
    private void establecerFecha() {
        this.fecha = new Date();
    }

    /**
     * @return the borrado
     */
    public boolean isBorrado() {
        return borrado;
    }

    /**
     * @param borrado the borrado to set
     */
    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

}
