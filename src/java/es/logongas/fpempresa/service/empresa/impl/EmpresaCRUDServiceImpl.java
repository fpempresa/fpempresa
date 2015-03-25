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
package es.logongas.fpempresa.service.empresa.impl;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.impl.CRUDServiceImpl;

/**
 *
 * @author logongas
 */
public class EmpresaCRUDServiceImpl extends CRUDServiceImpl<Empresa, Integer> implements CRUDService<Empresa, Integer> {

    private Usuario getPrincipal() {
        return (Usuario) principal;
    }

    @Override
    public void insert(Empresa empresa) throws BusinessException {

        //REGLA SEGURIDAD
        if (getPrincipal() == null) {
            throw new BusinessException("No es posible modificar la empresa si no estás autenticado");
        }
        
        //REGLA SEGURIDAD: Solo puede modificarlo administradores y centros
        if ((getPrincipal().getTipoUsuario() != TipoUsuario.ADMINISTRADOR) && (getPrincipal().getTipoUsuario() != TipoUsuario.CENTRO)) {
            throw new BusinessException("Solo un administrador o un profesor puede insertar una empresa");
        }
        
        if (getPrincipal().getTipoUsuario() == TipoUsuario.CENTRO) {
            empresa.setCentro(getPrincipal().getCentro());
        }        
        
        super.insert(empresa);
    }

    @Override
    public boolean update(Empresa empresa) throws BusinessException {
        Empresa empresaOriginal = this.readOriginal(empresa.getIdEmpresa());

        //REGLA SEGURIDAD
        if (getPrincipal() == null) {
            throw new BusinessException("No es posible modificar la empresa si no estás autenticado");
        }
        
        //REGLA SEGURIDAD: Solo puede modificarlo administradores y centros
        if ((getPrincipal().getTipoUsuario() != TipoUsuario.ADMINISTRADOR) && (getPrincipal().getTipoUsuario() != TipoUsuario.CENTRO)) {
            throw new BusinessException("Solo un administrador o un profesor puede modificar una empresa");
        }

        
        if (getPrincipal().getTipoUsuario() == TipoUsuario.CENTRO) {
            if (empresaOriginal.getCentro().getIdCentro()!=empresa.getCentro().getIdCentro()) {
                throw new BusinessException("No es posible modificar el centro al que pertenece la empresa");
            }
        }     
        


        return super.update(empresa);
    }

}
