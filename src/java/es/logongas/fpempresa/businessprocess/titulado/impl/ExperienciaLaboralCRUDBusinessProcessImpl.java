/*
 * FPempresa Copyright (C) 2024 Lorenzo González
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
package es.logongas.fpempresa.businessprocess.titulado.impl;

import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleContext;
import es.logongas.ix3.rule.RuleGroupPredefined;
import java.util.Date;

/**
 *
 * @author logongas
 */
public class ExperienciaLaboralCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<ExperienciaLaboral, Integer> implements CRUDBusinessProcess<ExperienciaLaboral, Integer> {
    
    
    @ConstraintRule(message = "La fecha final debe ser mayor o igual que la inicial.", groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private boolean isFechaFinMayorFechaInicio(RuleContext<ExperienciaLaboral> ruleContext) {
        Principal principal=ruleContext.getPrincipal();
        Date fechaInicioRule=ruleContext.getEntity().getFechaInicio();
        Date fechaFinRule=ruleContext.getEntity().getFechaFin();
        Usuario usuario=null;
        
        if (principal instanceof Usuario) {
            usuario=(Usuario)principal;
        }
        
        if ((usuario!=null) && (usuario.getTipoUsuario()==TipoUsuario.ADMINISTRADOR)) {
            return true;
        }

        
        if ((fechaInicioRule!=null) && (fechaFinRule!=null)) {
            if (fechaInicioRule.after(fechaFinRule)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }    
    
}
