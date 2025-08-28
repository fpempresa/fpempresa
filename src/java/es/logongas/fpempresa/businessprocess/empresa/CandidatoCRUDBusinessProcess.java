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
package es.logongas.fpempresa.businessprocess.empresa;

import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
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

    byte[] getFotoCandidato(FotoCandidatoArguments fotoCandidatoArguments) throws BusinessException;
    byte[] getCurriculumCandidato(CurriculumCandidatoArguments curriculumCandidatoArguments) throws BusinessException;

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
    
    public class CurriculumCandidatoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Candidato candidato;

        public CurriculumCandidatoArguments() {
        }

        public CurriculumCandidatoArguments(Principal principal, DataSession dataSession, Candidato candidato) {
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
