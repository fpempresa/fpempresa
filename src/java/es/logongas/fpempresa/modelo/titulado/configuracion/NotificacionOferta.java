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
package es.logongas.fpempresa.modelo.titulado.configuracion;

import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleGroupPredefined;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author logongas
 */
public class NotificacionOferta {
    private boolean notificarPorEmail;
    
    private Set<Provincia> provincias;

    public NotificacionOferta() {
        this.provincias = new HashSet<Provincia>();
        this.notificarPorEmail=false;
    }
    
    @ConstraintRule(message = "Debe seleccionar al menos una provincia",fieldName = "provincias",groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private boolean almenosUnaProvincia() {
        
        if (notificarPorEmail==true) {
            if ((provincias==null) || (provincias.isEmpty())) {
                return false;
            } else {
                return true;
            }
            
        } else {
            return true;
        }
    }

    /**
     * @return the notificarPorEmail
     */
    public boolean isNotificarPorEmail() {
        return notificarPorEmail;
    }

    /**
     * @param notificarPorEmail the notificarPorEmail to set
     */
    public void setNotificarPorEmail(boolean notificarPorEmail) {
        this.notificarPorEmail = notificarPorEmail;
    }

    /**
     * @return the provincia
     */
    public Set<Provincia> getProvincias() {
        return provincias;
    }

    /**
     * @param provincias the provincia to set
     */
    public void setProvincias(Set<Provincia> provincias) {
        this.provincias = provincias;
    }
    
}
