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

import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
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

    @Override
    public void notificarOfertaATitulados(DataSession dataSession, Oferta oferta) throws BusinessException {
        TituladoCRUDService tituladoCRUDService = (TituladoCRUDService) serviceFactory.getService(Titulado.class);
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        List<Titulado> tituladosSuscritos = tituladoCRUDService.getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(dataSession, oferta);
        for (Titulado titulado : tituladosSuscritos) {
            try {
                Mail mail = new Mail();
                Usuario usuario = usuarioCRUDService.getUsuarioFromTitulado(dataSession, titulado.getIdTitulado()); //TODO esto no es muy eficiente
                mail.addTo(usuario.getEmail());
                mail.setSubject("Nueva oferta de trabajo en una de tus provincias seleccionadas");
                mail.setHtmlBody("Hay una nueva oferta de trabajo en una de tus provincias seleccionadas. Accede a tu cuenta para poder visualizarla.");
                mail.setFrom(Config.getSetting("mail.sender"));

                mailService.send(mail);
            } catch (IOException ex) {
                throw new RuntimeException("Error al enviar email notificacion oferta a titulados", ex);
            }
        }
    }

}
