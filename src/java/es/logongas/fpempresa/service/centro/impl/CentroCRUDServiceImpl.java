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
package es.logongas.fpempresa.service.centro.impl;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.impl.CRUDServiceImpl;

/**
 *
 * @author logongas
 */
public class CentroCRUDServiceImpl extends CRUDServiceImpl<Centro, Integer> implements  CRUDService<Centro, Integer> {

    private Usuario getPrincipal() {
        return (Usuario) principal;
    }

    @Override
    public void insert(Centro centro) throws BusinessException {

        if ((getPrincipal() == null) || (getPrincipal().getTipoUsuario() != TipoUsuario.ADMINISTRADOR)) {
            throw new BusinessException("Solo un administrador puede insertar centros");
        }

        super.insert(centro);
    }

    @Override
    public boolean update(Centro centro) throws BusinessException {
        Centro centroOriginal = this.readOriginal(centro.getIdCentro());

        //REGLA SEGURIDAD: No se puede modificar el tipo del centro
        if (getPrincipal() == null) {
            throw new BusinessException("No es posible modificar el centro si no estás autenticado");
        }
        
        //REGLA SEGURIDAD: Solo puede modificarlo administradores y centros
        if ((getPrincipal().getTipoUsuario() != TipoUsuario.ADMINISTRADOR) && (getPrincipal().getTipoUsuario() != TipoUsuario.CENTRO)) {
            throw new BusinessException("Solo un administrador o un profesor puede modificar un centro");
        }

        
        if (getPrincipal().getTipoUsuario() == TipoUsuario.CENTRO) {
            if (getPrincipal().getCentro().getIdCentro()!=centro.getIdCentro()) {
                throw new BusinessException("Solo puedes modificar el centro al que perteneces");
            }
            
            
            if (centroOriginal.getEstadoCentro()!=centro.getEstadoCentro()) {
                throw new BusinessException("No puedes modificar el estado del centro");
            }            
        }        
        


        return super.update(centro);
    }

}
