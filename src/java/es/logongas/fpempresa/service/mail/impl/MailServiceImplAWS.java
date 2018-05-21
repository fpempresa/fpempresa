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
package es.logongas.fpempresa.service.mail.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.service.mail.Attach;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Servicio de envio de EMails.
 *
 * @author rhuffus
 */
public class MailServiceImplAWS implements MailService {

    @Override
    public void send(Mail mail) throws IOException {
        try {
            Session session = Session.getDefaultInstance(new Properties());
            MimeMessage message = new MimeMessage(session);
            
            message.setSubject(mail.getSubject(), "UTF-8");
            message.setFrom(new InternetAddress(mail.getFrom()));
            message.setRecipients(javax.mail.Message.RecipientType.TO, getAddresses(mail.getTo()));
            
            
            MimeMultipart mimeMultiPart = new MimeMultipart("alternative");
            
            if ((mail.getTextBody()!=null) && (mail.getTextBody().isEmpty()==false)) {
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(mail.getTextBody(), "text/plain; charset=UTF-8");
                mimeMultiPart.addBodyPart(textPart);
            }
            
            if ((mail.getHtmlBody()!=null) && (mail.getHtmlBody().isEmpty()==false)) {
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(mail.getHtmlBody(), "text/html; charset=UTF-8");
                mimeMultiPart.addBodyPart(htmlPart);
            }
            
            MimeBodyPart contentBodyPart = new MimeBodyPart();
            contentBodyPart.setContent(mimeMultiPart);
            
            
            MimeMultipart mainBodyPart = new MimeMultipart("mixed");
            mainBodyPart.addBodyPart(contentBodyPart);
            message.setContent(mainBodyPart);
            

            for (Attach attach : mail.getAttachs()) {
                MimeBodyPart att = new MimeBodyPart();
                DataSource dataSource = new InputStreamDataSource(attach.getMimeType(), attach.getFileName(), attach.getData());
                att.setDataHandler(new DataHandler(dataSource));
                att.setFileName(dataSource.getName());
                mainBodyPart.addBodyPart(att);
            }

            
            //Aquí es el proceso de envio
            AWSCredentials credentials = new BasicAWSCredentials(Config.getSetting("aws.accessKey"), Config.getSetting("aws.secretKey"));
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceAsyncClient(credentials);
            Region REGION = Region.getRegion(Regions.EU_WEST_1);
            client.setRegion(REGION);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
            client.sendRawEmail(rawEmailRequest);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setEntityType(Class t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class getEntityType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Address[] getAddresses(List<String> addresses) {
        try {
            List<Address> addressesList = new ArrayList<Address>();

            for (String address : addresses) {
                addressesList.add(new InternetAddress(address));
            }

            Address[] addressesArray = new Address[addressesList.size()];
            addressesList.toArray(addressesArray);
            return addressesArray;
        } catch (AddressException ex) {
            throw new RuntimeException(ex);
        }
    }

    private class InputStreamDataSource implements DataSource {

        private final String contentType;
        private final String name;
        private final byte[] data;

        public InputStreamDataSource(String contentType, String name, byte[] data) {
            this.contentType = contentType;
            this.name = name;
            this.data = data;
        }

        public String getContentType() {
            return contentType;
        }

        public String getName() {
            return name;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

}
