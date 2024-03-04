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

import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.businessprocess.security.PostArguments;
import es.logongas.ix3.security.model.ACE;
import es.logongas.ix3.security.model.ACE.ConditionalScriptEvaluator;
import es.logongas.ix3.security.model.Identity;
import java.util.List;
/**
 * Solo se pueden leer usuarios de tipo centro y de mi mismo centro
 * aceType='Deny'
 * idPermission=23 (PostExecuteBusinessProcess)
 * idIdentity=33 (GCentros)
 * secureResourceRegExp='.*BusinessProcess.Usuario.read';
 * @author logongas
 */
public class ConditionalScriptEvaluatorCentroDenyUsuarioRead implements ConditionalScriptEvaluator {

    @Override
    public boolean evaluate(ACE ace, Identity identity, String secureResourceName, Object arguments, List<String> mg) {

        Usuario usuario = (Usuario) ((PostArguments) arguments).result;
        if (identity.getIdIdentity() == usuario.getIdIdentity()) {
            return false;
        }
        int idCentro = ((Usuario) identity).getCentro().getIdCentro();

        if (usuario.getTipoUsuario() == TipoUsuario.CENTRO) {

            if (usuario.getCentro().getIdCentro() == idCentro) {
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }

    }
}
