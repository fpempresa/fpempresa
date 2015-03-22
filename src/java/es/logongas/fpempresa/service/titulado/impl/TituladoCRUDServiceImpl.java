/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado.impl;

import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.service.impl.CRUDServiceImpl;

/**
 *
 * @author logongas
 */
public class TituladoCRUDServiceImpl extends CRUDServiceImpl<Titulado, Integer> implements TituladoCRUDService {

    @Override
    public void insert(Titulado titulado) throws BusinessException {

        try {
            Usuario usuario = (Usuario) principal;         
            
            if ((usuario.getTipoUsuario()==TipoUsuario.TITULADO) && (usuario.getTitulado()!=null)) {
                throw new BusinessException("Ya existe un usuario asociado a ese titulado");
            }
            
            
            transactionManager.begin();

            getDAO().insert(titulado);
            
            if (usuario.getTipoUsuario()==TipoUsuario.TITULADO) {
                //Si un usuario de tipo titulado está añadiendo un titulado
                //Decimos que ese usuario está asociado a ese titulado
                usuario.setTitulado(titulado);
                daoFactory.getDAO(Usuario.class).update(usuario);
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
