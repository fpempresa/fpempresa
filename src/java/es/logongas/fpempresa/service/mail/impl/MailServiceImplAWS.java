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
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;

/**
 * Servicio de envio de EMails por AWS Simple Email Service 
 *
 * @author rhuffus
 */
public class MailServiceImplAWS implements MailService {

    @Override
    public void send(Mail mail) {
        try {
            Session session = Session.getDefaultInstance(new Properties());

            Message message=JavaMailHelper.getMessage(mail, session);
            
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





}
