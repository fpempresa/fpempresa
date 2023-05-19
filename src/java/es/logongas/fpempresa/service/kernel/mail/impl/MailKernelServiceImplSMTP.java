/**
 * FPempresa Copyright (C) 2018 Lorenzo González
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
package es.logongas.fpempresa.service.kernel.mail.impl;

import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.service.kernel.mail.Mail;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import es.logongas.fpempresa.service.kernel.mail.MailKernelService;

/**
 * Servicio e envio de EMails por SMTP
 * @author logongas
 */
public class MailKernelServiceImplSMTP implements MailKernelService {

    @Override
    public void send(Mail mail) {
        try {
            String smtpServer = Config.getSetting("smtp.server");
            String smtpUserName = Config.getSetting("smtp.userName");
            String smtpPassword = Config.getSetting("smtp.password");

            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.host", smtpServer);
            properties.put("mail.smtp.auth", "true");

            Authenticator authenticator = new SMTPAuthenticator(smtpUserName, smtpPassword);

            Session session = Session.getDefaultInstance(properties, authenticator);

            Message message=JavaMailHelper.getMessage(mail, session);

            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message,message.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    
    private class SMTPAuthenticator extends javax.mail.Authenticator {

        private final String userName;
        private final String password;

        public SMTPAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }




}
