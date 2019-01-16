/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

        Page<Candidato> page=candidatoCRUDService.getCandidatosOferta(dataSession, oferta, true, false, 0, pageRequest);
        
        for(Candidato candidato:page.getContent()) {
            candidatoCRUDService.notificarCandidatoAEmpresas(dataSession, candidato);
        }
        
    }

}
