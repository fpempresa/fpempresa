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
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.NamedSearch;
import es.logongas.ix3.service.impl.CRUDServiceImpl;

/**
 *
 * @author logongas
 */
public class CandidatoCRUDServiceImpl extends CRUDServiceImpl<Candidato, Integer> implements CRUDService<Candidato, Integer> {

    private Usuario getPrincipal() {
        return (Usuario) principalLocator.getPrincipal();
    }

    private CandidatoDAO getCandidatoDAO() {
        return (CandidatoDAO) getDAO();
    }

    @Override
    public void insert(Candidato candidato) throws BusinessException {

        if (getCandidatoDAO().isUsuarioCandidato(candidato.getUsuario(), candidato.getOferta()) == true) {
            throw new BusinessException("Ya estás inscrito en la oferta");
        }

        super.insert(candidato); 
    }

    
    @NamedSearch(parameterNames = {"oferta","ocultarRechazados","hanTrabajado","maxAnyoTitulo","pageRequest"})
    public Page<Candidato> getCandidatosOferta(Oferta oferta,boolean ocultarRechazados,boolean hanTrabajado,int maxAnyoTitulo,PageRequest pageRequest) {
       return getCandidatoDAO().getCandidatosOferta(oferta, ocultarRechazados, hanTrabajado, maxAnyoTitulo, pageRequest);
    }

}
