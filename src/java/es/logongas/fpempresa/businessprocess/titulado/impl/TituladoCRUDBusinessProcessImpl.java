/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.titulado.impl;

import es.logongas.fpempresa.businessprocess.titulado.TituladoCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;

/**
 *
 * @author logongas
 */
public class TituladoCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Titulado, Integer> implements TituladoCRUDBusinessProcess {

    @Override
    public Titulado insert(InsertArguments<Titulado> insertArguments) throws BusinessException {
        try {
            Titulado titulado;
            Usuario usuario = (Usuario) insertArguments.principal;

            transactionManager.begin(insertArguments.dataSession);

            if (usuario.getTipoUsuario() == TipoUsuario.TITULADO) {

                Usuario empresario = serviceFactory.getService(Usuario.class).read(insertArguments.dataSession, usuario.getIdIdentity());
                empresario.setTitulado(insertArguments.entity);
                empresario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                serviceFactory.getService(Usuario.class).update(insertArguments.dataSession, empresario);
            }
            titulado = serviceFactory.getService(Titulado.class).insert(insertArguments.dataSession, insertArguments.entity);

            transactionManager.commit(insertArguments.dataSession);

            return titulado;

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

    @Override
    public void importarTitulados(ImportarTituladosArguments importarTituladosArguments) throws BusinessException {
        TituladoCRUDService tituladoCRUDService = (TituladoCRUDService) serviceFactory.getService(Titulado.class);
        tituladoCRUDService.importarTitulados(importarTituladosArguments.dataSession, importarTituladosArguments.multipartFile);
    }

}
