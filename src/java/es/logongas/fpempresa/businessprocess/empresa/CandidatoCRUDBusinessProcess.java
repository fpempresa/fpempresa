/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.empresa;

import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;

/**
 *
 * @author logongas
 */
public interface CandidatoCRUDBusinessProcess extends CRUDBusinessProcess<Candidato, Integer> {

    byte[] getFotoCandidato(FotoCandidatoArguments fotoCandidatoArguments);

    public Page<Candidato> getCandidatosOferta(GetCandidatosOfertaArguments getCandidatosOfertaArguments) throws BusinessException;

    public long getNumCandidatosOferta(GetNumCandidatosOferta getNumCandidatosOferta) throws BusinessException;

    public class FotoCandidatoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Candidato candidato;

        public FotoCandidatoArguments() {
        }

        public FotoCandidatoArguments(Principal principal, DataSession dataSession, Candidato candidato) {
            super(principal, dataSession);

            this.candidato = candidato;
        }
    }
    
    

    public class GetCandidatosOfertaArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Oferta oferta;
        public boolean ocultarRechazados;
        public boolean certificados;
        public int maxAnyoTitulo;
        public PageRequest pageRequest;

        public GetCandidatosOfertaArguments() {
        }

        public GetCandidatosOfertaArguments(Principal principal, DataSession dataSession, Oferta oferta, boolean ocultarRechazados, boolean certificados, int maxAnyoTitulo, PageRequest pageRequest) {
            super(principal, dataSession);
            this.oferta = oferta;
            this.ocultarRechazados = ocultarRechazados;
            this.certificados = certificados;
            this.maxAnyoTitulo = maxAnyoTitulo;
            this.pageRequest = pageRequest;
        }

        
    }
    
    public class GetNumCandidatosOferta extends CRUDBusinessProcess.ParametrizedSearchArguments {
        public Oferta oferta;

        public GetNumCandidatosOferta() {
        }

        public GetNumCandidatosOferta(Principal principal, DataSession dataSession, Oferta oferta) {
            super(principal, dataSession);
            this.oferta = oferta;
        }
        
    }

}
