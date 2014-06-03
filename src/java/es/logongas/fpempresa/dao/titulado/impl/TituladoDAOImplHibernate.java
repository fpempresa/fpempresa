/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.logongas.fpempresa.dao.titulado.impl;

import es.logongas.fpempresa.modelo.comun.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import es.logongas.ix3.security.authentication.Principal;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Lorenzo
 */
public class TituladoDAOImplHibernate extends GenericDAOImplHibernate<Titulado,Integer> {

    @Autowired
    Principal principal;    
    
    @Override
    protected void preInsertBeforeTransaction(Session session, Titulado titulado) {
        titulado.setUsuario((Usuario)principal);
    }
    @Override
    protected void preUpdateBeforeTransaction(Session session, Titulado titulado) {
        titulado.setUsuario((Usuario)principal);
    }    
}
