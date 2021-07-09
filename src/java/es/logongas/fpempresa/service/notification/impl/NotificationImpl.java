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
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.security.publictoken.PublicTokenCancelarSubcripcion;
import es.logongas.fpempresa.service.mail.Attach;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.security.jwt.Jws;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class NotificationImpl implements Notification {
    private static final Logger logMail = LogManager.getLogger(Mail.class);
    
    final static String PIE_RGPD_MAIL="De conformidad con lo dispuesto en la Ley Orgánica 3/2018, de 5 de diciembre, de Protección de Datos Personales y garantía de los derechos digitales y el Reglamento (UE) 2016/679 del Parlamento Europeo y del Consejo de 27 de abril de 2016, informamos que los datos personales serán incluidos en un fichero titularidad y responsabilidad de ASOCIACION DE CENTROS DE FORMACION PROFESIONAL FPEMPRESA con la finalidad de posibilitar las comunicaciones a través del correo electrónico de la misma con los distintos contactos que ésta mantiene dentro del ejercicio de su actividad.Podrá ejercer los derechos de acceso, rectificación, supresión y demás derechos reconocidos en la normativa mencionada, en la siguiente dirección C/ PADRE AMIGÓ Nº 25 28025 MADRID o a través de la siguiente dirección de correo electrónico " + Config.getSetting("app.correoSoporte") + ". Solicite más información dirigiéndose al correo electrónico indicado.En virtud de la Ley 34/2002 de 11 de Julio de Servicios de la Sociedad de la Información y Correo Electrónico (LSSI-CE), este mensaje y sus archivos adjuntos pueden contener información confidencial, por lo que se informa de que su uso no autorizado está prohibido por la ley. Si ha recibido este mensaje por equivocación, por favor notifíquelo inmediatamente a través de esta misma vía y borre el mensaje original junto con sus ficheros adjuntos sin leerlo o grabarlo total o parcialmente.";
    final static String BAJA_BY_EMAIL="<a href=\"mailto:" + Config.getSetting("app.correoSoporte") + "?Subject=Deseo%20darme%20de%20baja%20de%20EmpleaFP%20y%20que%20sean%20borrados%20todos%20mis%20datos\">Darse de baja de EmpleaFP</a>";
    
    @Autowired
    MailService mailService;

    @Autowired
    ReportService reportService;
    
    @Autowired
    Conversion conversion;
    
    @Autowired
    CRUDServiceFactory serviceFactory;   
    
    @Autowired
    Jws jws;      
    
    
    @Override
    public void nuevaOferta(Usuario usuario, Oferta oferta) {
        byte[] secretToken=usuario.getSecretToken().getBytes(Charset.forName("utf-8"));
        int idIdentity=usuario.getIdIdentity();
        
        PublicTokenCancelarSubcripcion publicTokenCancelarSubcripcion=new PublicTokenCancelarSubcripcion(idIdentity, jws, secretToken);
        
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setSubject("Nueva oferta de trabajo en EmpleaFP: " + oferta.getPuesto());
        mail.setHtmlBody("<strong>*** No respondas a este correo, ha sido enviado automáticamente ***</strong><br><br>"
                + "En <a href=\"" + getAppURL() + "\">EmpleaFP</a> hay una nueva oferta de trabajo en el municipio de " + oferta.getMunicipio() + " de la empresa \"" + StringEscapeUtils.escapeHtml4(oferta.getEmpresa()+"") + "\"." 
                + "<br>Si tienes interés en ella, deberás entrar en <a href=\"" + getAppURL() + "\">EmpleaFP</a> e <strong>inscribirte en la oferta.</strong><br><br>"
                + "<strong>\"" + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(oferta.getDescripcion())) + "\"</strong><br>"    
                + "<br>"
                + "<br>"
                + "Si tienes interés en la oferta,  deberás entrar en <a href=\"" + getAppURL() + "\">EmpleaFP</a> e <strong>inscribirte en la oferta.</strong><br>"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)
                + "<br><br>EmpleaFP te ha enviado este correo electrónico porque te has registrado como titulado en la web " + getAppURL() +" y has marcado que deseas recibir notificaciones de nuevas ofertas de empleo."
                + "<br><br>Para que no te volvamos a enviar correos con nuevas ofertas de empleo pincha <a href=\"" + getAppURL() + "/site/index.html#/cancelar-suscripcion/" + idIdentity + "/" + publicTokenCancelarSubcripcion.toString() + "\">aquí</a>."
        );
        mail.setFrom(Config.getSetting("mail.sender"));
        sendMail(mail);
    }

    @Override
    public void nuevoCandidato(DataSession dataSession, Candidato candidato) {
        Mail mail = new Mail();
        Oferta oferta = candidato.getOferta();
        if (oferta==null) {
            throw new NullPointerException("oferta is null");
        }
        Empresa empresa=oferta.getEmpresa();
        if (empresa==null) {
            throw new NullPointerException("empresa is null");
        }
        Contacto contacto=empresa.getContacto();
        String direccionEMail=null;
        String persona=null;
        if (contacto!=null) {
            direccionEMail=contacto.getEmail();
            persona=contacto.getPersona()+"";
        } 
        
        if ((direccionEMail==null) || (direccionEMail.trim().isEmpty())) {
            logMail.warn("No se ha enviado correo del candidato a la empresa " + empresa.getIdEmpresa()  + " al no existir el correo");
            return;
        }

        mail.addTo(direccionEMail);
        mail.setSubject("Nuevo candidato en EmpleaFP para tu oferta de trabajo: " + oferta.getPuesto());
        mail.setHtmlBody("<strong>*** No respondas a este correo, ha sido enviado automáticamente ***</strong><br><br>"
                + "En <a href=\"" + getAppURL() + "\">EmpleaFP</a> hay un nuevo candidato que se ha subscrito a una de tus ofertas:<br>"
                + "<h4>Datos del candidato</h4>"
                + "Nombre: " + StringEscapeUtils.escapeHtml4(candidato.getUsuario().getNombre()) + " " + StringEscapeUtils.escapeHtml4(candidato.getUsuario().getApellidos()) + "<br>"
                + "Email: " + candidato.getUsuario().getEmail() + "<br><br>" 
                + "<h4>Datos de la oferta</h4>"
                + "Puesto: " + StringEscapeUtils.escapeHtml4(oferta.getPuesto()) + "<br>"
                + "Descripción: " + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(oferta.getDescripcion()))
                + "Accede a tu cuenta de <a href=\"" + getAppURL() + "\">EmpleaFP</a> para más información.<br><br><br><br>"
                + "Si necesitas ayuda puedes contactar en " + Config.getSetting("app.correoSoporte") + "."
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)
                + "<br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL)
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
        mail.setSubject("Resetear contraseña en EmpleaFP");
        mail.setHtmlBody(""
                + "Has solicitado cambiar tu contraseña en <a href=\"" + getAppURL() + "\">EmpleaFP</a>.<br><br>"
                + "Para proceder al cambio de contraseña de tu cuenta haz click en el siguiente enlace e introduce tu nueva contraseña: \n"
                + "<a href=\"" + getAppURL() + "/site/index.html#/resetear-contrasenya/" + usuario.getIdIdentity() + "/" + usuario.getClaveResetearContrasenya() + "\">Resetear contraseña</a>"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)                
                + "<br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL)                
        );
        sendMail(mail);
    }

    @Override
    public void validarCuenta(Usuario usuario) {
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("Confirma tu correo para acceder a EmpleaFP");
        mail.setHtmlBody(""
                + "Bienvenido " + usuario.getNombre() + " " + usuario.getApellidos() + ",<br><br>"
                + "Acabas de registrarte en <a href=\"" + getAppURL() + "\">EmpleaFP</a>, la mayor bolsa de trabajo específica de la Formación Profesional.<br> "
                + "Para poder completar tu registro es necesario que verifiques tu dirección de correo haciendo click en el siguiente enlace: "
                + "<a href=\"" + getAppURL() + "/site/index.html#/validar-email/" + usuario.getIdIdentity() + "/" + usuario.getClaveValidacionEmail() + "\">Verificar Email</a>"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)               
                + "<br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL)               
        );
        sendMail(mail);
    }

    
    @Override
    public void mensajeSoporte( String nombre, String correo,String mensaje) {
        Mail mail = new Mail();
        mail.addTo(Config.getSetting("app.correoSoporte"));
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setReply(correo);
        mail.setSubject("Petición de soporte de " + nombre);
        mail.setHtmlBody(""
                + StringEscapeUtils.escapeHtml4(nombre) + " ha enviado el siguiente mensaje para el soporte de " + getAppURL() + ":<br>"
                + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(mensaje))
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)          
                + "<br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL)          
        );
        
        sendMail(mail);
    }
    
    @Override
    public void usuarioInactivo(Usuario usuario) {
        String msg=
                  " Hola " + StringEscapeUtils.escapeHtml4(usuario.getNombre()) + ",<br>"
                + "hace tiempo que no has accedido a tu cuenta de <a href='" + getAppURL() + "'>EmpleaFP.com</a>.<br>"
                + "La normativa en protección de datos nos obliga tratar en todo momento con datos actualizados de tu currículum.<br>"
                + "<strong>Si no accedes antes de 15 días</strong> a tu cuenta de <a href='" + getAppURL() + "'>EmpleaFP.com</a>, procederemos a <strong>borrarla</strong>.";
        
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("Tu cuenta de EmpleaFP.com va a ser borrada por falta de uso");
        mail.setHtmlBody(msg + "<br><br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL));
        
        sendMail(mail);
    }  
    
    @Override
    public void exception(String subject, String msg, Throwable throwable) {
        Mail mail = new Mail();
        mail.addTo(Config.getSetting("app.correoSoporte"));
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject(subject);
        mail.setTextBody(getAppURL()+"\n"+msg+"\n"+getStackTraceAsString(throwable));
        
        sendMail(mail);
    }
    
    
    private void sendMail(Mail mail) {
        if (isEnabledEMailNotifications()) {
            mailService.send(mail);
            logMail.info("Enviado correo:" + mail.getTo().get(0) + ":" + mail.getSubject() );
        } else {
            logMail.info("Correo NO enviado:" + mail.getTo().get(0) + ":" + mail.getSubject() );
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

    private String getStackTraceAsString(Throwable throwable) {
        if (throwable==null) {
            return "";
        }
        
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        String stackTrace=stringWriter.toString();
        
        return stackTrace;
    }
    
}
