/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.empresa.impl;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;

/**
 *
 * @author logongas
 */
public class EmpresaCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Empresa, Integer> implements CRUDBusinessProcess<Empresa, Integer> {

    @Override
    public Empresa insert(InsertArguments<Empresa> insertArguments) throws BusinessException {
        try {
            Empresa empresa;

            Usuario usuario = (Usuario) insertArguments.principal;

            if (usuario.getTipoUsuario() == TipoUsuario.EMPRESA) {

                transactionManager.begin(insertArguments.dataSession);
                Usuario empresario = serviceFactory.getService(Usuario.class).read(insertArguments.dataSession, usuario.getIdIdentity());
                empresario.setEmpresa(insertArguments.entity);
                empresario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                serviceFactory.getService(Usuario.class).update(insertArguments.dataSession, empresario);
                
                
                empresa = serviceFactory.getService(Empresa.class).insert(insertArguments.dataSession, insertArguments.entity);

                transactionManager.commit(insertArguments.dataSession);
            } else if (usuario.getTipoUsuario() == TipoUsuario.CENTRO) {
                Centro centro = daoFactory.getDAO(Centro.class).read(insertArguments.dataSession, usuario.getCentro().getIdCentro());
                insertArguments.entity.setCentro(centro);
                empresa = super.insert(insertArguments);
            } else {
                empresa = super.insert(insertArguments);
            }

            return empresa;
        } catch (BusinessException ex) {
            if (transactionManager.isActive(insertArguments.dataSession)) {
                transactionManager.rollback(insertArguments.dataSession);
            }
            throw ex;
        } catch (Exception ex) {
            if (transactionManager.isActive(insertArguments.dataSession)) {
                transactionManager.rollback(insertArguments.dataSession);
            }
            throw new RuntimeException(ex);
        }
    }

}
