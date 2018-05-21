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
import es.logongas.fpempresa.dao.empresa.CandidatoDAO;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.mail.Attach;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class CandidatoCRUDServiceImpl extends CRUDServiceImpl<Candidato, Integer> implements CandidatoCRUDService {

    @Autowired
    MailService mailService;
    
    @Autowired
    ReportService reportService;    

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

    public Page<Candidato> getCandidatosOferta(DataSession dataSession, Oferta oferta, boolean ocultarRechazados, boolean certificados, int maxAnyoTitulo, PageRequest pageRequest) {
        return getCandidatoDAO().getCandidatosOferta(dataSession, oferta, ocultarRechazados, certificados, maxAnyoTitulo, pageRequest);
    }

    public long getNumCandidatosOferta(DataSession dataSession, Oferta oferta) throws BusinessException {
        return getCandidatoDAO().getNumCandidatosOferta(dataSession, oferta);
    }

    @Override
    public void notificarCandidatoAEmpresas(DataSession dataSession, Candidato candidato) throws BusinessException {
        if (candidato.getOferta().getEmpresa().getCentro() == null) {
            try {
                Mail mail = new Mail();
                Oferta oferta = candidato.getOferta();
                mail.addTo(candidato.getOferta().getEmpresa().getContacto().getEmail());
                mail.setSubject("Nuevo candidato para la oferta de trabajo: " + oferta.getPuesto());
                mail.setHtmlBody("Hola <strong>" + oferta.getEmpresa().getContacto().getPersona() + "</strong>,<br><br>"
                        + "Un nuevo candidato se ha suscrito a una de tus ofertas:<br>"
                        + "<h4>Datos de la oferta</h4>"
                        + "<strong>Provincia: </strong>" + oferta.getMunicipio().getProvincia() + "<br>"
                        + "<strong>Municipio: </strong>" + oferta.getMunicipio() + "<br>"
                        + "<strong>Ciclos: </strong>" + oferta.getCiclos() + "<br>"
                        + "<strong>Familia: </strong>" + oferta.getFamilia() + "<br>"
                        + "<strong>Empresa: </strong>" + oferta.getEmpresa() + "<br>"
                        + "<strong>Puesto: </strong>" + oferta.getPuesto() + "<br>"
                        + "<strong>Descripción: </strong>" + oferta.getDescripcion()
                        + "<h4>Datos del candidato</h4>"
                        + "<strong>Nombre: </strong>" + candidato.getUsuario().getNombre() + " " + candidato.getUsuario().getApellidos() + "<br>"
                        + "<strong>Teléfono: </strong>" + candidato.getUsuario().getTitulado().getTelefono() + "<br>"
                        + "<strong>Email: </strong>" + candidato.getUsuario().getEmail() + "<br>" + "<br>"
                        + "Accede a tu cuenta de <a href=\"http://www.empleafp.com\">empleaFP</a> para poder ampliar la información"
                );
                mail.setFrom(Config.getSetting("mail.sender").toString());
                
                Map<String, Object> parameters=new HashMap<>();
                parameters.put("idIdentity", candidato.getUsuario().getIdIdentity());
                byte[] curriculum=reportService.exportToPdf(dataSession, "curriculum", parameters);
                
                mail.getAttachs().add(new Attach("curriculum.pdf", curriculum, "application/pdf"));
                
                mailService.send(mail);
            } catch (IOException ex) {
                throw new RuntimeException("Error al enviar email notificacion de nuevo candidato a la empresa", ex);
            }
        }
    }
}
