/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.service.empresa.impl;

import es.logongas.fpempresa.dao.empresa.CandidatoDAO;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class CandidatoCRUDServiceImpl extends CRUDServiceImpl<Candidato, Integer> implements CandidatoCRUDService {

    @Autowired
    Notification notification;
    


    private CandidatoDAO getCandidatoDAO() {
        return (CandidatoDAO) getDAO();
    }

    @Override
    public Candidato insert(DataSession dataSession, Candidato candidato) throws BusinessException {

        if (getCandidatoDAO().isUsuarioCandidato(dataSession, candidato.getUsuario(), candidato.getOferta()) == true) {
            throw new BusinessException("Ya estás inscrito en la oferta");
        }

        return super.insert(dataSession, candidato);
    }

    @Override
    public Page<Candidato> getCandidatosOferta(DataSession dataSession, Oferta oferta, boolean ocultarRechazados, boolean certificados, int maxAnyoTitulo, PageRequest pageRequest) {
        return getCandidatoDAO().getCandidatosOferta(dataSession, oferta, ocultarRechazados, certificados, maxAnyoTitulo, pageRequest);
    }

    @Override
    public long getNumCandidatosOferta(DataSession dataSession, Oferta oferta) throws BusinessException {
        return getCandidatoDAO().getNumCandidatosOferta(dataSession, oferta);
    }

    @Override
    public void notificarCandidatoAEmpresas(DataSession dataSession, Candidato candidato) throws BusinessException {
        if (candidato.getOferta().getEmpresa().getCentro() == null) {
            notification.nuevoCandidato(dataSession, candidato);
        }
    }
}
