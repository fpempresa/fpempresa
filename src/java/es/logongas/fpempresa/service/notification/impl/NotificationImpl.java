/**
 * FPempresa Copyright (C) 2019 Lorenzo González
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
package es.logongas.fpempresa.service.notification.impl;

import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.mail.Attach;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class NotificationImpl implements Notification {
    protected final Log log = LogFactory.getLog(getClass());
    
    final static String PIE_RGPD_MAIL="Este mensaje se dirige exclusivamente a  su destinatario y puede contener información privilegiada , confidencial , sujeta  al secreto profesional o datos de carácter personal, cuya difusión está prohibida por la Ley . Si no es la persona destinataria indicada, no puede  copiar este mensaje ni entregarlo a terceros bajo ningún concepto. Si ha recibido este mensaje por error o lo ha conseguido por otros medios, le pedimos que nos lo comunique inmediatamente por esta misma vía y lo elimine irreversiblemente.\nEn aras del cumplimiento del  Reglamento  (UE)  2016/679  del  Parlamento  Europeo  y  del  Consejo,  de  27  de  abril  de  2016 , la Asociación de Centros de Formación Profesional, garantiza la confidencialidad de los datos personales de sus clientes.\n\nLe comunicamos que su dirección de correo electrónico forma parte de una base de datos gestionada bajo la responsabilidad de Asociación de Centros de Formación Profesional, con la única finalidad de prestarle los servicios por usted solicitados, por su condición de cliente, proveedor, o porque nos haya solicitado información en algún momento. Es voluntad de Asociación de Centros de Formación Profesional, evitar el envío deliberado de correo no solicitado, por lo cual podrá en todo momento, ejercitar sus derechos de acceso, rectificación, cancelación , oposición , portabilidad de datos u olvido de sus datos de carácter personal por correo ordinario a: Asociación de Centros de Formación Profesional, IES Puerta Bonita C/ Padre Amigó, 5 CP 28025 Madrid (Madrid) o en el correo electrónico soporte@empleafp.com";
    
    @Autowired
    MailService mailService;

    @Autowired
    ReportService reportService;
    
    @Autowired
    Conversion conversion;
    
    @Override
    public void nuevaOferta(Usuario usuario, Oferta oferta) {
        Mail mail = new Mail();
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
                + "<strong>Descripción: </strong>" + toHTMLRetornoCarro(oferta.getDescripcion()) + "<br>" + "<br>"
                + "Accede a tu cuenta de <a href=\"" + getAppURL() + "\">empleaFP</a> para poder ampliar la información"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)
        );
        mail.setFrom(Config.getSetting("mail.sender"));
        sendMail(mail);
    }

    @Override
    public void nuevoCandidato(DataSession dataSession, Candidato candidato) {
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
                + "Accede a tu cuenta de <a href=\"" + getAppURL() + "\">empleaFP</a> para poder ampliar la información"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)
        );
        mail.setFrom(Config.getSetting("mail.sender"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idIdentity", candidato.getUsuario().getIdIdentity());
        byte[] curriculum = reportService.exportToPdf(dataSession, "curriculum", parameters);

        mail.getAttachs().add(new Attach("curriculum.pdf", curriculum, "application/pdf"));

        sendMail(mail);
    }

    @Override
    public void resetearContrasenya(Usuario usuario) {
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("Resetear contraseña en empleaFP");
        mail.setHtmlBody(""
                + "Has solicitado cambiar tu contraseña en <a href=\"" + getAppURL() + "\">empleaFP</a>.<br><br>"
                + "Para proceder al cambio de contraseña de tu cuenta haz click en el siguiente enlace e introduce tu nueva contraseña: \n"
                + "<a href=\"" + getAppURL() + "/site/index.html#/resetear-contrasenya/" + usuario.getClaveResetearContrasenya() + "\">Resetear contraseña</a>"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)                
        );
        sendMail(mail);
    }

    @Override
    public void validarCuenta(Usuario usuario) {
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("Confirma tu correo para acceder a empleaFP");
        mail.setHtmlBody(""
                + "Bienvenido <strong>" + usuario.getNombre() + " " + usuario.getApellidos() + "</strong>,<br><br>"
                + "Acabas de registrarte en <a href=\"" + getAppURL() + "\">empleaFP</a>, la mayor bolsa de trabajo específica de la Formación Profesional.<br> "
                + "Para poder completar tu registro es necesario que verifiques tu dirección de correo haciendo click en el siguiente enlace: "
                + "<a href=\"" + getAppURL() + "/site/index.html#/validar-email/" + usuario.getClaveValidacionEmail() + "\">Verificar Email</a>"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)               
        );
        sendMail(mail);
    }

    private void sendMail(Mail mail) {
        if (isEnabledEMailNotifications()) {
             mailService.send(mail);
             log.info("Enviado correo:" + mail.getTo().get(0) + ":" + mail.getSubject() );
        } else {
            log.info("Correo NO enviado:" + mail.getTo().get(0) + ":" + mail.getSubject() );
        }
    }
    
    
    private String toHTMLRetornoCarro(String plainText) {
        if (plainText == null) {
            return null;
        }

        return plainText.replaceAll("\n", "<br>");
    }    
    
    @Override
    public void setEntityType(Class t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class getEntityType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private boolean isEnabledEMailNotifications() {
        Boolean enabledEMailNotifications=(Boolean)conversion.convertFromString(Config.getSetting("app.enabledEMailNotifications"),Boolean.class);
        if (enabledEMailNotifications==null) {
            return false;
        } else {
            return enabledEMailNotifications;
        }
    }
    
    public String getAppURL() {
        return Config.getSetting("app.url");
    }
    
}
