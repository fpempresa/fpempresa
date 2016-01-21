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

import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author logongas
 */
public class OfertaCRUDServiceImpl extends CRUDServiceImpl<Oferta, Integer> implements OfertaCRUDService {


    private OfertaDAO getOfertaDAO() {
        return (OfertaDAO) getDAO();
    }

    @Override
    public List<Oferta> getOfertasUsuarioTitulado(DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) throws BusinessException {
        return getOfertaDAO().getOfertasUsuarioTitulado(dataSession, usuario, provincia, fechaInicio, fechaFin);
    }
    
    @Override
    public List<Oferta> getOfertasInscritoUsuarioTitulado(DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) throws BusinessException {
        return getOfertaDAO().getOfertasInscritoUsuarioTitulado(dataSession, usuario, provincia, fechaInicio, fechaFin);
    }

    @Override
    public List<Oferta> getOfertasEmpresasCentro(DataSession dataSession, Centro centro) throws BusinessException {
        return getOfertaDAO().getOfertasEmpresasCentro(dataSession, centro);
    }

    @Override
    public List<Oferta> getOfertasEmpresa(DataSession dataSession, Empresa empresa) throws BusinessException {
        return getOfertaDAO().getOfertasEmpresa(dataSession, empresa);
    }

}
