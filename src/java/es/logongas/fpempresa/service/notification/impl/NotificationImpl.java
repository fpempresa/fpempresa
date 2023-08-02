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
import es.logongas.fpempresa.security.publictoken.PublicTokenCerrarOferta;
import es.logongas.fpempresa.service.kernel.mail.Attach;
import es.logongas.fpempresa.service.kernel.mail.Mail;
import es.logongas.fpempresa.service.kernel.mail.MailKernelService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.security.jwt.Jws;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Date;
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
    MailKernelService mailKernelService;

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
        String url=getAppURL() + "/site/index.html#/cancelar-suscripcion/" + idIdentity + "/" + publicTokenCancelarSubcripcion.toString();
        
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Nueva oferta de empleo";
        bodyContent.parrafos="Hola " + StringEscapeUtils.escapeHtml4(usuario.getNombre()) + ","
                + "<br>tenemos una nueva oferta de empleo que puede interesarte."
                + "<br><br>Es de la empresa <strong>'" + StringEscapeUtils.escapeHtml4(oferta.getEmpresa()+"") + "'</strong> en <strong>'" + StringEscapeUtils.escapeHtml4(oferta.getMunicipio()+"") + "'</strong> para el puesto de <strong>'" + StringEscapeUtils.escapeHtml4(oferta.getPuesto()) + "'</strong>."
                + "<br><br>Si tienes interés en ella, deberás entrar en <a href=\"" + getAppURL() + "/site/index.html#/login\">EmpleaFP</a> e inscribirte en la oferta."
                + "<br><br><p style=\"font-style: italic;padding-left: 10px;color:#383838 \">" + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(oferta.getDescripcion())) + "</p>"
                + "<strong>Si tienes interés en la oferta,  deberás entrar en <a href=\"" + getAppURL() + "/site/index.html#/login\">EmpleaFP</a> e inscribirte en la oferta</strong>"
                + "<br><br><strong>*** No respondas a este correo, ha sido enviado automáticamente ***</strong>";
        bodyContent.labelButton="Acceder a EmpleaFP para inscribirte en la Oferta";
        bodyContent.linkButton=getAppURL() + "/site/index.html#/login";
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+"<br><br>EmpleaFP te ha enviado este correo electrónico porque te has registrado como titulado en la web " + getAppURL() +" y has marcado que deseas recibir notificaciones de nuevas ofertas de empleo.<br><br>Para que no te volvamos a enviar correos con nuevas ofertas de empleo pincha <a href=\"" + url + "\">aquí</a>."
                + "<br>¿No te funciona el anterior enlace para dejar de recibir correos? Pega el siguiente enlace en el navegador:<br>"+url;
        
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setSubject("Nueva oferta de empleo en EmpleaFP: " + oferta.getPuesto());
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        mail.setFrom(Config.getSetting("mail.sender"));
        sendMail(mail);
    }

    @Override
    public void inscritoCandidato(DataSession dataSession, Candidato candidato) {

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
        if (contacto!=null) {
            direccionEMail=contacto.getEmail();
        } 
        
        if ((direccionEMail==null) || (direccionEMail.trim().isEmpty())) {
            logMail.warn("No se ha enviado correo del candidato a la empresa " + empresa.getIdEmpresa()  + " al no existir el correo");
            return;
        }
        
        byte[] secretToken=oferta.getSecretToken().getBytes(Charset.forName("utf-8"));
        int idOferta=oferta.getIdOferta();
        
        PublicTokenCerrarOferta publicTokenCerrarOferta=new PublicTokenCerrarOferta(idOferta, jws, secretToken);
        
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Nuevo candidato";
        bodyContent.parrafos="Un nuevo candidato llamado <strong>" + StringEscapeUtils.escapeHtml4(candidato.getUsuario().getNombre()) + " " + StringEscapeUtils.escapeHtml4(candidato.getUsuario().getApellidos()) + "</strong> se ha inscrito en la siguiente oferta:" 
                + "<p style=\"font-style: italic;padding-left:10px\">" + StringEscapeUtils.escapeHtml4(oferta.getPuesto()) + "</p>"
                + "Se ha adjuntado un PDF con su currículum en este correo."
                + "<br><br>Si desea cerrar la oferta y que de esa forma que no le lleguen nuevos correos de esta oferta, pinche <a href=\"" + getAppURL() + "/site/index.html#/cerrar-oferta/" + idOferta + "/" + publicTokenCerrarOferta.toString() + "\">aquí</a>."
                + "<br><br><strong>*** No responda a este correo, ha sido enviado automáticamente ***</strong>";
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br><br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL);

        
        Mail mail = new Mail();
        mail.addTo(direccionEMail);
        mail.setSubject("Nuevo candidato en EmpleaFP de su oferta: " + oferta.getPuesto());
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        mail.setFrom(Config.getSetting("mail.sender"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idIdentity", candidato.getUsuario().getIdIdentity());
        byte[] curriculum = reportService.exportToPdf(dataSession, "curriculum", parameters);

        mail.getAttachs().add(new Attach("curriculum.pdf", curriculum, "application/pdf"));

        sendMail(mail);
    }
    
    @Override
    public void desinscritoCandidato(DataSession dataSession, Candidato candidato) {

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
        if (contacto!=null) {
            direccionEMail=contacto.getEmail();
        } 
        
        if ((direccionEMail==null) || (direccionEMail.trim().isEmpty())) {
            logMail.warn("No se ha enviado correo del candidato desinscrito a la empresa al no existir el correo.idCandidato="+candidato.getIdCandidato());
            return;
        }

        String nombreCompletoTitulado=candidato.getUsuario().getNombre() + " " + candidato.getUsuario().getApellidos();
        
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Desinscrito candidato";
        bodyContent.parrafos="Un candidato llamado '" + StringEscapeUtils.escapeHtml4(nombreCompletoTitulado) + "' se ha <strong>desinscrito</strong> en la siguiente oferta que usted publicó:" 
                + "<p style=\"font-style: italic;padding-left:10px\">" + StringEscapeUtils.escapeHtml4(oferta.getPuesto())+ "</p>"
                + "En su día desde EmpleaFP se le envió un correo con un PDF con el curriculumn del candidato."
                + "<p style='color:#AB211C'>Si aun conserva el curriculum de '" + StringEscapeUtils.escapeHtml4(nombreCompletoTitulado) + "' deberá borrarlo así como cualquier otra información relativa al candidato</p>"
                + "<br><br><strong>*** No responda a este correo, ha sido enviado automáticamente ***</strong>";
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br><br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL);

        Mail mail = new Mail();
        mail.addTo(direccionEMail);
        mail.setSubject("Desinscrito candidato en EmpleaFP de su oferta '" + oferta.getPuesto() + "'");
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        mail.setFrom(Config.getSetting("mail.sender"));

        sendMail(mail);
    }
    
    
    @Override
    public void resetearContrasenya(Usuario usuario) {
        String url=getAppURL() + "/site/index.html#/resetear-contrasenya/" + usuario.getIdIdentity() + "/" + usuario.getClaveResetearContrasenya();
        
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Cambiar contraseña";
        bodyContent.parrafos="Has solicitado cambiar tu contraseña en <a href=\"" + getAppURL() + "\">EmpleaFP</a>. Para proceder al cambio de contraseña de tu cuenta haz click en el siguiente botón.";
        bodyContent.labelButton="Cambiar la contraseña";
        bodyContent.linkButton=url;
        bodyContent.belowButton="¿No te funciona el botón? Pega el siguiente enlace en el navegador:<br>"+url;
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br><br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL);
        
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("Cambiar contraseña en EmpleaFP");
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        sendMail(mail);
    }

    @Override
    public void validarCuenta(Usuario usuario) {
        String url=getAppURL() + "/site/index.html#/validar-email/" + usuario.getIdIdentity() + "/" + usuario.getClaveValidacionEmail();
        
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Confirmar tu dirección de correo";
        bodyContent.parrafos="Bienvenido " +  StringEscapeUtils.escapeHtml4(usuario.getNombre()) + ",<br><br>"
                + "Acabas de registrarte en <a href=\"" + getAppURL() + "\">EmpleaFP</a>, la mayor bolsa de trabajo específica de la Formación Profesional. Para poder completar tu registro es necesario que confirmes tu dirección de correo haciendo click en el siguiente botón: ";
        bodyContent.labelButton="Confirmar tu dirección de correo";
        bodyContent.linkButton=url;
        bodyContent.belowButton="¿No te funciona el botón? Pega el siguiente enlace en el navegador:<br>"+url;
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br><br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL);
        
        
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("Confirma tu dirección de correo para acceder a EmpleaFP");
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        sendMail(mail);
    }

    
    @Override
    public void mensajeSoporte( String nombre, String correo,String mensaje) {
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Petición de soporte";
        bodyContent.parrafos=StringEscapeUtils.escapeHtml4(nombre) + " ha enviado el siguiente mensaje para el soporte de " + getAppURL() + ":<br>" + toHTMLRetornoCarro(StringEscapeUtils.escapeHtml4(mensaje));
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br><br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL);
        
        
        Mail mail = new Mail();
        mail.addTo(Config.getSetting("app.correoSoporte"));
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setReply(correo);
        mail.setSubject("Petición de soporte de " + nombre);
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        
        sendMail(mail);
    }
    
    @Override
    public void usuarioInactivo(Usuario usuario) {
        BodyContent bodyContent=new BodyContent();
        bodyContent.titulo="Tu cuenta va a ser borrada pronto";
        bodyContent.parrafos="Hace tiempo que no has accedido a tu cuenta de <a href='" + getAppURL() + "'>EmpleaFP</a> que como sabrás es la mayor bolsa de empleo de la formación profesional." 
                + "<br><br>La normativa en protección de datos nos obliga tratar en todo momento con datos actualizados."
                + "<br><br><strong>Si no accedes antes de 15 días a tu cuenta de <a href='" + getAppURL() + "/site/index.html#/login'>EmpleaFP</a>, procederemos a borrarla</strong>."
                + "<br><br><strong>*** No respondas a este correo, ha sido enviado automáticamente ***</strong>";
        bodyContent.labelButton="Acceder a EmpleaFP para evitar el borrado de la cuenta";
        bodyContent.linkButton=getAppURL() + "/site/index.html#/login";
        bodyContent.pie=toHTMLRetornoCarro(PIE_RGPD_MAIL)+ "<br><br>"+toHTMLRetornoCarro(BAJA_BY_EMAIL);
        
        Mail mail = new Mail();
        mail.addTo(usuario.getEmail());
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject("[Acción Requerida] Tu cuenta de EmpleaFP va a ser borrada por falta de uso");
        mail.setHtmlBody(getBodyFromHTMLTemplate(bodyContent));
        
        sendMail(mail);
    }  
    
    @Override
    public void exceptionToAdministrador(String subject, String msg, Throwable throwable) {
        Mail mail = new Mail();
        mail.addTo(Config.getSetting("app.correoSoporte"));
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject(subject);
        mail.setTextBody(getAppURL()+"\n"+msg+"\n"+getStackTraceAsString(throwable));
        
        sendMail(mail);
    }
    
    @Override
    public void mensajeToAdministrador(String subject, String msg) {
        Mail mail = new Mail();
        mail.addTo(Config.getSetting("app.correoSoporte"));
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject(subject);
        mail.setTextBody(getAppURL()+"\n"+msg);
        
        sendMail(mail);
    }    
    
    private void sendMail(Mail mail) {
        if (isEnabledEMailNotifications()) {
            String subject=mail.getSubject();
            String to=mail.getTo().get(0);
            
            try {
                mailKernelService.send(mail);
                logMail.info("Enviado correo:" + subject + ":" + to);
            } catch (Exception ex) {
                logMail.error("!!!!!!!!!Falló al enviar el correo:'" + subject + "' a " + to + "." + ex.getMessage());
                throw new RuntimeException(ex);
            }
            
        } else {
            logMail.info("Correo NO enviado:" + mail.getSubject() + ":" + mail.getTo().get(0) );
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

    
    private String getBodyFromHTMLTemplate(BodyContent bodyContent) {
        Date date=new Date();
        int year=DateUtil.get(date, DateUtil.Interval.YEAR);
        
        
        String body = 
                  "<table align='center' cellpadding='0' cellspacing='0' border='0' style='width:100%;margin:0 auto;background-color:#ffffff'>\n"
                + "    <tr>\n"
                + "        <td style='font-size:0;'>&nbsp;</td>\n"
                + "        <td align='center' valign='top' style='width:580px;'>\n"
                + "            <table align='center' cellpadding='0' cellspacing='0' border='0' style='width:95%;'>\n"
                + "                <tr>\n"
                + "                    <td align='center'>\n"
                + "                        <table align='center' cellpadding='0' cellspacing='0' border='0' style='width:100%;'>\n"
                + "                            <tr>\n"
                + "                                <td align='left' style='padding:40px 0;border-bottom:1px solid #ABABAB;'>\n"
                + "                                    <a href='" + getAppURL() + "'  style='text-decoration:none;'>\n"
                + "                                        <img src='" + getAppURL() + "/img/logos/empleafp.png' alt=''  border='0' width='220' style='display:block;outline:0;padding:0;border:0;width:220px;height:auto;'>\n"
                + "                                    </a>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td height='20' style='height:20px;line-height:20px;font-size:0;'>\n"
                + "                                    &nbsp;\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <td align='center'>\n"
                + "                        <table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>\n"
                + "                            <tr>\n"
                + "                                <td align='center'>\n"
                + "                                    <table cellpadding='0' cellspacing='0' border='0' style='width:100%;' class='w100'>\n"
                + "                                        <tr>\n"
                + "                                            <td style='padding-top:20px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-weight:bold;font-size:24px;line-height:35px;color:#253858;text-align:left;'>\n"
                + "                                                <div  style='text-decoration:none;color:#253858;font-weight:bold;' >\n"
                + "                                                    " + bodyContent.titulo + "\n"
                + "                                                </div>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td style='padding-top:40px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:16px;line-height:26px;color:#42526E;text-align:left;'>\n"
                + "                                    " + bodyContent.parrafos + "<br><br>\n"
                + "                                </td>\n"
                + "                            </tr>\n";
        
                if ((bodyContent.linkButton!=null) || (bodyContent.labelButton!=null)) {
                    body=body+
                          "                            <tr>\n"
                        + "                                <td align='center' style='padding-top:30px;'>\n"
                        + "                                    <table cellpadding='0' cellspacing='0' border='0'>\n"
                        + "                                        <tr>\n"
                        + "                                            <td align='center' style='border-radius:3px;background-color:#0056B8;'>\n"
                        + "                                                <a href='" + bodyContent.linkButton + "' target='_blank'  style='display:inline-block;border:1px solid #0056B8;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:16px;color:#ffffff;text-decoration:none;border-radius: 4px; padding:12px;'>\n"
                        + "                                                    " + bodyContent.labelButton + "\n"
                        + "                                                </a>\n"
                        + "                                            </td>\n"
                        + "                                        </tr>\n"
                        + "                                    </table>\n"
                        + "                                </td>\n"
                        + "                            </tr>\n";
                }
                if (bodyContent.belowButton!=null) {
                    body=body+
                          "                            <tr>\n"
                        + "                                <td style='padding-top:40px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;line-height:19px;color:#42526E;text-align:left;'>\n"
                        + "                                    " + bodyContent.belowButton + "<br><br>\n"
                        + "                                </td>\n"
                        + "                            </tr>\n";
                }
                
                body=body+
                  "                            <tr>\n"
                + "                                <td style='padding-top:40px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:16px;line-height:22px;color:#42526E;text-align:left;'>\n"
                + "                                    Atentamente,<br>el equipo de EmpleaFP.\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <td align='center'>\n"
                + "                        <table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>\n"
                + "                            <tr>\n"
                + "                                <td align='center' style='padding-top:40px;padding-bottom:40px;'>\n"
                + "                                    <table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>\n"
                + "                                        <tr>\n"
                + "                                            <td align='justify' style='border-top:1px solid #ABABAB;padding-top:40px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;line-height:19px;color:#707070;text-align:justify;'>\n"
                + "                                                " + bodyContent.pie + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        <tr>\n"
                + "                                            <td style='padding-top:20px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;line-height:19px;color:#707070;text-align:center;'>\n"
                + "                                                <a href='" + getAppURL() + "/site/index.html#/legal/aviso-legal' style='font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;color:#707070;text-decoration:none;'>Aviso Legal</a>\n"
                + "                                                •\n"
                + "                                                <a href='" + getAppURL() + "/site/index.html#/legal/politica-privacidad' style='font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;color:#707070;text-decoration:none;'>Política de Privacidad</a>\n"
                + "                                                •\n"
                + "                                                <a href='" + getAppURL() + "/site/index.html#/legal/terminos-uso' style='font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;color:#707070;text-decoration:none;'>Términos de uso</a>\n"
                + "                                                •\n"
                + "                                                <a href='" + getAppURL() + "/site/index.html#/docs/soporte' style='font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;color:#707070;text-decoration:none;'>Soporte</a>\n"                        
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        <tr>\n"
                + "                                            <td align='center' style='padding-top:10px;font-family:Helvetica neue, Helvetica, Arial, Verdana, sans-serif;font-size:13px;line-height:19px;color:#707070;text-align:center;'>\n"
                + "                                                <a href=\"" + getAppURL() + "\" style=\"color: inherit;text-decoration: inherit;\">Copyright 2013-" + year + " Asociación de Centros de Formación Profesional - FPEmpresa</a>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "        </td>\n"
                + "        <td style='font-size:0;'>&nbsp;</td>\n"
                + "    </tr>\n"
                + "</table>";

        return body;
    }
    
    
    private class BodyContent {
        String titulo="";
        String parrafos="";
        String pie="";
        String labelButton=null;
        String linkButton=null;
        String belowButton=null;

    }
    
    
}
