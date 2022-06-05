/*
 * FPempresa Copyright (C) 2022 Lorenzo González
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

import es.logongas.fpempresa.businessprocess.titulado.FormacionAcademicaCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleContext;
import es.logongas.ix3.rule.RuleGroupPredefined;

/**
 *
 * @author logongas
 */
public class FormacionAcademicaCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<FormacionAcademica, Integer> implements FormacionAcademicaCRUDBusinessProcess {

    
    @ConstraintRule(message = "El ciclo es requerido", groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private boolean isCheckCicloRequerido(RuleContext<FormacionAcademica> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        FormacionAcademica formacionAcademica = ruleContext.getEntity();
        
        if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
            if (formacionAcademica.getCiclo()==null) {
                return false;
            }

        }

        return true;

    }
    @ConstraintRule(message = "El centro educativo es requerido", groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private boolean isCheckCentro(RuleContext<FormacionAcademica> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        FormacionAcademica formacionAcademica = ruleContext.getEntity();
        
        if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
            if (formacionAcademica.getCentro()==null) {
                return false;
            }

        }

        return true;

    }    
}
