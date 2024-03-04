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

import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.ix3.security.model.ACE;
import es.logongas.ix3.security.model.ACE.ConditionalScriptEvaluator;
import es.logongas.ix3.security.model.Identity;
import java.util.List;
import java.util.Set;


/**
 * No se puede obtener la foto de titulados
 * aceType='Allow'
 * idPermission=22 (PreExecuteBusinessProcess)
 * idIdentity=33 (GCentros) 
 * secureResourceRegExp='UsuarioCRUDBusinessProcess.Usuario.getFoto';
 * @author logongas
 */
public class ConditionalScriptEvaluatorCentroAllowUsuarioGetFoto implements ConditionalScriptEvaluator {

    @Override
    public boolean evaluate(ACE ace, Identity identity, String secureResourceName, Object arguments, List<String> mg) {
        //Los centros ya no pueden ver las fotos de los alumnos
        return false;
        
//        Set<FormacionAcademica> formacionesAcademicas = ((UsuarioCRUDBusinessProcess.GetFotoArguments) arguments).usuario.getTitulado().getFormacionesAcademicas();
//        int idCentro = ((Usuario) identity).getCentro().getIdCentro();
//
//        if (this.isRealizadoEnCentro(formacionesAcademicas, idCentro)) {
//            return true;
//        } else {
//            return false;
//        }

    }

    private boolean isRealizadoEnCentro(Set<FormacionAcademica> formacionesAcademicas, int idCentro) {
        for (FormacionAcademica formacionAcademica : formacionesAcademicas) {
            if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
                if (formacionAcademica.getCentro().getIdCentro() == idCentro) {
                    return true;
                }
            }
        }
        return false;
    }
}
