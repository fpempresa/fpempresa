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

    @Override
    public Candidato insert(InsertArguments<Candidato> insertArguments) throws BusinessException {
        Candidato Candidato = super.insert(insertArguments);
        CandidatoCRUDService CandidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);
        CandidatoCRUDService.notificarCandidatoAEmpresas(insertArguments.dataSession, Candidato);
        return Candidato;
    }

}
