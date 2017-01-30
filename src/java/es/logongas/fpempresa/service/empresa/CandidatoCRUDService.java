/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.empresa;

import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDService;

/**
 *
 * @author logongas
 */
public interface CandidatoCRUDService extends CRUDService<Candidato, Integer> {

    public Page<Candidato> getCandidatosOferta(DataSession dataSession, Oferta oferta, boolean ocultarRechazados, boolean certificados, int maxAnyoTitulo, PageRequest pageRequest);

    public long getNumCandidatosOferta(DataSession dataSession, Oferta oferta) throws BusinessException;
    
    public void notificarCandidatoAEmpresas(DataSession dataSession, Candidato candidato) throws BusinessException;
}
