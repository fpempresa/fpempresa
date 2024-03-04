/*
 * FPempresa Copyright (C) 2023 Lorenzo González
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
package es.logongas.fpempresa.security.ace;

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.ix3.businessprocess.security.PostArguments;
import es.logongas.ix3.security.model.ACE;
import es.logongas.ix3.security.model.ACE.ConditionalScriptEvaluator;
import es.logongas.ix3.security.model.Identity;
import java.util.List;
import java.util.Set;

/**
 * aceType='Deny'
 * idPermission=23 (PostExecuteBusinessProcess)
 * idIdentity=32 (GTitulados)
 * secureResourceRegExp='.*BusinessProcess.Oferta.read';
 * @author logongas
 */
public class ConditionalScriptEvaluatorTituladoDenyOfertaRead implements ConditionalScriptEvaluator {

    @Override
    public boolean evaluate(ACE ace, Identity identity, String secureResourceName, Object arguments, List<String> mg) {

        Set<Ciclo> ciclosOferta = ((Oferta) ((PostArguments) arguments).result).getCiclos();
        Set<FormacionAcademica> formacionesAcademicas = ((Usuario) identity).getTitulado().getFormacionesAcademicas();

        for (Ciclo ciclo : ciclosOferta) {
            if (this.isRealizadoCiclo(formacionesAcademicas, ciclo.getIdCiclo()) == true) {
                return false;
            }

        }

        return true;
    }

    private boolean isRealizadoCiclo(Set<FormacionAcademica> formacionesAcademicas, int idCiclo) {
        for (FormacionAcademica formacionAcademica : formacionesAcademicas) {
            if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
                if (formacionAcademica.getCiclo().getIdCiclo() == idCiclo) {
                    return true;
                }
            }
        }
        return false;
    }
}
