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

import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
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
        return (Usuario) principalLocator.getPrincipal();
    }

    @Override
    public void insert(Empresa empresa) throws BusinessException {

            if (getPrincipal().getEmpresa()!=null) {
                throw new BusinessException("No puedes insertar una empresa puesta que ya perteneces a " + getPrincipal().getEmpresa().toString());
            }        
        
            if (getPrincipal().getTipoUsuario() == TipoUsuario.CENTRO) {
                if (getPrincipal().getEstadoUsuario()!=EstadoUsuario.ACEPTADO) {
                    throw new BusinessException("No puedes insertar una empresa ya que no estas aceptado en el centro");
                }
            }             
            
            if (getPrincipal().getTipoUsuario() == TipoUsuario.CENTRO) {
                empresa.setCentro(getPrincipal().getCentro());
            }                
        
            if (getPrincipal().getTipoUsuario()==TipoUsuario.EMPRESA) {
                getPrincipal().setEmpresa(empresa);
                getPrincipal().setEstadoUsuario(EstadoUsuario.ACEPTADO);
            }       
            
            transactionManager.begin();

                getDAO().insert(empresa);
                daoFactory.getDAO(Usuario.class).update(getPrincipal());

            transactionManager.commit();
    }

}
