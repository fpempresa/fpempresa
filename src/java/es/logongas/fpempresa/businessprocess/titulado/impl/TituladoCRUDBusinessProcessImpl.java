/**
 * FPempresa Copyright (C) 2020 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
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
import java.util.List;

/**
 *
 * @author logongas
 */
public class TituladoCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Titulado, Integer> implements TituladoCRUDBusinessProcess {

    @Override
    public Titulado insert(InsertArguments<Titulado> insertArguments) throws BusinessException {
        try {
            Titulado titulado=insertArguments.entity;
            Usuario usuario = (Usuario) insertArguments.principal;

            transactionManager.begin(insertArguments.dataSession);

            if (usuario.getTipoUsuario() == TipoUsuario.TITULADO) {

                Usuario usuarioTitulado = serviceFactory.getService(Usuario.class).read(insertArguments.dataSession, usuario.getIdIdentity());
                usuarioTitulado.setTitulado(insertArguments.entity);
                usuarioTitulado.setEstadoUsuario(EstadoUsuario.ACEPTADO);
                serviceFactory.getService(Usuario.class).update(insertArguments.dataSession, usuarioTitulado);
                
                if (usuarioTitulado.isAceptarEnvioCorreos()) {
                    titulado.getConfiguracion().getNotificacionOferta().setNotificarPorEmail(true);
                }
                
            }
            titulado = serviceFactory.getService(Titulado.class).insert(insertArguments.dataSession, titulado);

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

    @Override
    public int getNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta(GetNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta getNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta) throws BusinessException {
        TituladoCRUDService tituladoCRUDService = (TituladoCRUDService) serviceFactory.getService(Titulado.class);
        
        List<Titulado> titulados=tituladoCRUDService.getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(getNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta.dataSession, getNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta.oferta);
        
        if (titulados==null) {
            throw new RuntimeException("La lista de titulados no puede ser null");
        } 
        
        return titulados.size();
        
    }

}
