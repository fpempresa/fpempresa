/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.empresa.impl;

import es.logongas.fpempresa.businessprocess.empresa.CandidatoCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;

/**
 *
 * @author logongas
 */
public class CandidatoCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Candidato, Integer> implements CandidatoCRUDBusinessProcess {

    @Override
    public byte[] getFotoCandidato(FotoCandidatoArguments fotoCandidatoArguments) {
        return fotoCandidatoArguments.candidato.getUsuario().getFoto();
    }

    @Override
    public Page<Candidato> getCandidatosOferta(GetCandidatosOfertaArguments getCandidatosOfertaArguments) throws BusinessException {
        CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);
        return candidatoCRUDService.getCandidatosOferta(getCandidatosOfertaArguments.dataSession, getCandidatosOfertaArguments.oferta, getCandidatosOfertaArguments.ocultarRechazados, getCandidatosOfertaArguments.certificados, getCandidatosOfertaArguments.maxAnyoTitulo, getCandidatosOfertaArguments.pageRequest);
    }

    @Override
    public long getNumCandidatosOferta(GetNumCandidatosOferta getNumCandidatosOferta) throws BusinessException {
        CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);

        return candidatoCRUDService.getNumCandidatosOferta(getNumCandidatosOferta.dataSession, getNumCandidatosOferta.oferta);
    }

}
