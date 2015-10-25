/**
 *   FPempresa
 *   Copyright (C) 2015  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.service.titulado.impl;

import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.impl.CRUDServiceImpl;

/**
 *
 * @author logongas
 */
public class TituladoCRUDServiceImpl extends CRUDServiceImpl<Titulado, Integer> implements CRUDService<Titulado, Integer> {

    @Override
    public void insert(Titulado titulado) throws BusinessException {

        try {
            Usuario usuario = (Usuario) principalLocator.getPrincipal();         
            
            if ((usuario.getTipoUsuario()==TipoUsuario.TITULADO) && (usuario.getTitulado()!=null)) {
                throw new BusinessException("Ya existe un usuario asociado a ese titulado");
            }
            
            
            transactionManager.begin();

            getDAO().insert(titulado);
            
            if (usuario.getTipoUsuario()==TipoUsuario.TITULADO) {
                UsuarioDAO usuarioDAO=(UsuarioDAO)daoFactory.getDAO(Usuario.class);
                Usuario updateUsuario=usuarioDAO.read(usuario.getIdIdentity());
                //Si un usuario de tipo titulado está añadiendo un titulado
                //Decimos que ese usuario está asociado a ese titulado
                updateUsuario.setTitulado(titulado);
                usuarioDAO.update(updateUsuario);
            }

            transactionManager.commit();
        } catch (BusinessException ex) {  
            if (transactionManager.isActive()) {
                transactionManager.rollback();
            }
            throw ex;
        } catch (Exception ex) {
            if (transactionManager.isActive()) {
                transactionManager.rollback();
            }
            throw new RuntimeException(ex);
        }
    }

}
