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

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class OfertaCRUDServiceImpl extends CRUDServiceImpl<Oferta, Integer> implements OfertaCRUDService {

    @Autowired
    protected CRUDServiceFactory serviceFactory;

    @Autowired
    MailService mailService;

    private OfertaDAO getOfertaDAO() {
        return (OfertaDAO) getDAO();
    }

    @Override
    public boolean delete(DataSession dataSession, Oferta entity) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);
        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);

            Filters filters = new Filters();
            filters.add(new Filter("oferta.idOferta", entity.getIdOferta(), FilterOperator.eq));

            List<Candidato> candidatos = candidatoCRUDService.search(dataSession, filters, null, null);
            for (Candidato candidato : candidatos) {
                candidatoCRUDService.delete(dataSession, candidato);
            }

            boolean success = super.delete(dataSession, entity);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            return success;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

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

    @Asynchronous
    @Override
    public void notificarOfertaATitulados(DataSession dataSession, Oferta oferta) throws BusinessException {
        TituladoCRUDService tituladoCRUDService = (TituladoCRUDService) serviceFactory.getService(Titulado.class);
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        List<Titulado> tituladosSuscritos = tituladoCRUDService.getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(dataSession, oferta);
        for (Titulado titulado : tituladosSuscritos) {
  
            Mail mail = new Mail();
            Usuario usuario = usuarioCRUDService.getUsuarioFromTitulado(dataSession, titulado.getIdTitulado()); //TODO esto no es muy eficiente
            mail.addTo(usuario.getEmail());
            mail.setSubject("Nueva oferta de trabajo: " + oferta.getPuesto());
            mail.setHtmlBody("Hola <strong>" + usuario.getNombre() + " " + usuario.getApellidos() + "</strong>,<br><br>"
                    + "Hay una nueva oferta de trabajo en una de tus provincias seleccionadas:<br>"
                    + "<strong>Provincia: </strong>" + oferta.getMunicipio().getProvincia() + "<br>"
                    + "<strong>Municipio: </strong>" + oferta.getMunicipio() + "<br>"
                    + "<strong>Ciclos: </strong>" + oferta.getCiclos() + "<br>"
                    + "<strong>Familia: </strong>" + oferta.getFamilia() + "<br>"
                    + "<strong>Empresa: </strong>" + oferta.getEmpresa() + "<br>"
                    + "<strong>Puesto: </strong>" + oferta.getPuesto() + "<br>"
                    + "<strong>Descripción: </strong>" + oferta.getDescripcion() + "<br>" + "<br>"
                    + "Accede a tu cuenta de <a href=\"http://www.empleafp.com\">empleaFP</a> para poder ampliar la información"
            );
            mail.setFrom(Config.getSetting("mail.sender").toString());
            mailService.send(mail);

        }
    }

}
