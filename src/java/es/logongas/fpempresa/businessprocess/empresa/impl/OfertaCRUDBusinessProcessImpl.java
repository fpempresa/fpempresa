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



package es.logongas.fpempresa.businessprocess.empresa.impl;

import es.logongas.fpempresa.businessprocess.empresa.OfertaCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 *
 * @author logongas
 */
public class OfertaCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Oferta, Integer> implements OfertaCRUDBusinessProcess {

    @Override
    public List<Oferta> getOfertasUsuarioTitulado(GetOfertasUsuarioTituladoArguments getOfertasUsuarioTituladoArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasUsuarioTitulado(getOfertasUsuarioTituladoArguments.dataSession, getOfertasUsuarioTituladoArguments.usuario, getOfertasUsuarioTituladoArguments.provincia, getOfertasUsuarioTituladoArguments.fechaInicio, getOfertasUsuarioTituladoArguments.fechaFin);
    }

    @Override
    public List<Oferta> getOfertasInscritoUsuarioTitulado(GetOfertasInscritoUsuarioTituladoArguments getOfertasInscritoUsuarioTituladoArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasInscritoUsuarioTitulado(getOfertasInscritoUsuarioTituladoArguments.dataSession, getOfertasInscritoUsuarioTituladoArguments.usuario, getOfertasInscritoUsuarioTituladoArguments.provincia, getOfertasInscritoUsuarioTituladoArguments.fechaInicio, getOfertasInscritoUsuarioTituladoArguments.fechaFin);
    }

    @Override
    public List<Oferta> getOfertasEmpresasCentro(GetOfertasEmpresasCentroArguments getOfertasEmpresasCentroArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasEmpresasCentro(getOfertasEmpresasCentroArguments.dataSession, getOfertasEmpresasCentroArguments.centro);
    }

    @Override
    public List<Oferta> getOfertasEmpresa(GetOfertasEmpresaArguments getOfertasEmpresaArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasEmpresa(getOfertasEmpresaArguments.dataSession, getOfertasEmpresaArguments.empresa);
    }

    @Override
    public Oferta insert(InsertArguments<Oferta> insertArguments) throws BusinessException {
        Oferta oferta = super.insert(insertArguments);
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);
        ofertaCRUDService.notificarOfertaATitulados(insertArguments.dataSession, oferta);
        return oferta;
    }

    @Override
    public void notificacionOferta(NotificacionOfertaArguments notificacionOfertaArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);
        CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);
        
        Oferta oferta=notificacionOfertaArguments.oferta;
        DataSession dataSession=notificacionOfertaArguments.dataSession;
        
        ofertaCRUDService.notificarOfertaATitulados(dataSession, oferta);
        
        PageRequest pageRequest=new PageRequest(0, 10000);

        Page<Candidato> page=candidatoCRUDService.getCandidatosOferta(dataSession, oferta, true, false, Integer.MAX_VALUE, pageRequest);
        
        for(Candidato candidato:page.getContent()) {
            candidatoCRUDService.notificarAEmpresaInscritoCandidato(dataSession, candidato);
        }
        
    }

    @Override
    public void cerrarOferta(CerrarOfertaArguments cerrarOfertaArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        Oferta oferta=cerrarOfertaArguments.oferta;
        DataSession dataSession=cerrarOfertaArguments.dataSession;
        String publicToken=cerrarOfertaArguments.publicToken;
        
        ofertaCRUDService.cerrarOferta(dataSession, oferta, publicToken);
        
    }

    
    
}
