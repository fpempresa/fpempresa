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
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.mail.Attach;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.util.HashMap;
import java.util.List;
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
    protected final Logger log = LogManager.getLogger(getClass());
    
    final static String PIE_RGPD_MAIL="De conformidad con lo dispuesto en la Ley Orgánica 3/2018, de 5 de diciembre, de Protección de Datos Personales y garantía de los derechos digitales y el Reglamento (UE) 2016/679 del Parlamento Europeo y del Consejo de 27 de abril de 2016, informamos que los datos personales serán incluidos en un fichero titularidad y responsabilidad de ASOCIACION DE CENTROS DE FORMACION PROFESIONAL FPEMPRESA con la finalidad de posibilitar las comunicaciones a través del correo electrónico de la misma con los distintos contactos que ésta mantiene dentro del ejercicio de su actividad.\n\nPodrá ejercer los derechos de acceso, rectificación, supresión y demás derechos reconocidos en la normativa mencionada, en la siguiente dirección C/ PADRE AMIGÓ Nº 25 28025 MADRID o a través de la siguiente dirección de correo electrónico " + Config.getSetting("app.correoSoporte") + ". Solicite más información dirigiéndose al correo electrónico indicado.\n\nEn virtud de la Ley 34/2002 de 11 de Julio de Servicios de la Sociedad de la Información y Correo Electrónico (LSSI-CE), este mensaje y sus archivos adjuntos pueden contener información confidencial, por lo que se informa de que su uso no autorizado está prohibido por la ley. Si ha recibido este mensaje por equivocación, por favor notifíquelo inmediatamente a través de esta misma vía y borre el mensaje original junto con sus ficheros adjuntos sin leerlo o grabarlo total o parcialmente.\n\n<a href=\"mailto:" + Config.getSetting("app.correoSoporte") + "?Subject=Deseo%20darme%20de%20baja%20de%20EmpleaFP%20y%20que%20sean%20borrados%20todos%20mis%20datos\">Darse de baja de EmpleaFP</a>";
    
    @Autowired
    MailService mailService;

    @Autowired
    ReportService reportService;
    
    @Autowired
    Conversion conversion;
    
    @Autowired
    CRUDServiceFactory serviceFactory;    
    
    @Override
    public void nuevaOferta(Usuario usuario, Oferta oferta) {
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setSubject("Nueva oferta de trabajo: " + oferta.getPuesto());
        mail.setHtmlBody("Hola " + usuario.getNombre() + ",<br>"
                + "hay una nueva oferta de trabajo en el municipio de " + oferta.getMunicipio() + " de la empresa \"" + StringEscapeUtils.escapeHtml4(oferta.getEmpresa()+"") + "\".<br>Si tienes interés en ella, deberás entrar en <a href=\"" + getAppURL() + "\">EmpleaFP</a> e <strong>inscribirte en la oferta.</strong><br><br>"
                + "<strong>\"" + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(oferta.getDescripcion())) + "\"</strong><br>"    
                + "<br>"
                + "<br>"
                + "<br>"
                + "<br>"
                + "Si necesitas ayuda puedes contactar con nosotros en " + Config.getSetting("app.correoSoporte") + "."
                + "<br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)
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
            UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

            List<Usuario> usuarios;
            try {
                usuarios = usuarioCRUDService.getUsuariosFromEmpresa(dataSession, empresa.getIdEmpresa());
            } catch (BusinessException ex) {
                throw new RuntimeException(ex);
            }
            
            if (usuarios.isEmpty()) {
                throw new RuntimeException("No existe ningun usuario al que notificar");
            }
            Usuario usuario=usuarios.get(0);
            direccionEMail=usuario.getEmail();
            persona=usuario.getNombre()+"";
            
            if ((direccionEMail==null) || (direccionEMail.trim().isEmpty())) {
                throw new RuntimeException("El usuario no tiene email:"+usuario.getLogin());
            }
        }
        
        
        mail.addTo(direccionEMail);
        mail.setSubject("Nuevo candidato para la oferta de trabajo: " + oferta.getPuesto());
        mail.setHtmlBody("Hola <strong>" + StringEscapeUtils.escapeHtml4(persona) + "</strong>,<br>"
                + "un nuevo candidato se ha suscrito a una de tus ofertas:<br>"
                + "<h4>Datos del candidato</h4>"
                + "Nombre: " + StringEscapeUtils.escapeHtml4(candidato.getUsuario().getNombre()) + " " + StringEscapeUtils.escapeHtml4(candidato.getUsuario().getApellidos()) + "<br>"
                + "Email: " + candidato.getUsuario().getEmail() + "<br><br>" 
                + "<h4>Datos de la oferta</h4>"
                + "Puesto: " + StringEscapeUtils.escapeHtml4(oferta.getPuesto()) + "<br>"
                + "Descripción: " + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(oferta.getDescripcion()))
                + "Accede a tu cuenta de <a href=\"" + getAppURL() + "\">EmpleaFP</a> para poder ampliar la información.<br><br><br><br>"
                + "Si necesitas ayuda puedes contactar en " + Config.getSetting("app.correoSoporte") + "."
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
        mail.setSubject("Resetear contraseña en EmpleaFP");
        mail.setHtmlBody(""
                + "Has solicitado cambiar tu contraseña en <a href=\"" + getAppURL() + "\">EmpleaFP</a>.<br><br>"
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
        mail.setSubject("Confirma tu correo para acceder a EmpleaFP");
        mail.setHtmlBody(""
                + "Bienvenido " + usuario.getNombre() + " " + usuario.getApellidos() + ",<br><br>"
                + "Acabas de registrarte en <a href=\"" + getAppURL() + "\">EmpleaFP</a>, la mayor bolsa de trabajo específica de la Formación Profesional.<br> "
                + "Para poder completar tu registro es necesario que verifiques tu dirección de correo haciendo click en el siguiente enlace: "
                + "<a href=\"" + getAppURL() + "/site/index.html#/validar-email/" + usuario.getClaveValidacionEmail() + "\">Verificar Email</a>"
                + "<br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL)               
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
        mail.setHtmlBody(msg + "<br><br><br><br>"+toHTMLRetornoCarro(PIE_RGPD_MAIL));
        
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
