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
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import java.io.IOException;

/**
 * Servicio de envio de EMails.
 *
 * @author rhuffus
 */
public class MailServiceImplAWS implements MailService {

    @Override
    public void send(Mail mail) throws IOException {

        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(mail.getTo());

        // Create the subject and body of the message.
        Content subject = new Content().withData(mail.getSubject());
        Content htmlBody = new Content().withData(mail.getHtmlBody());
        Content textBody = new Content().withData(mail.getTextBody());
        Body body = new Body();
        body.withHtml(htmlBody);
        body.withText(textBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(mail.getFrom()).withDestination(destination).withMessage(message);

        // Load AWS credentials
        AWSCredentials credentials = new BasicAWSCredentials(Config.getSetting("aws.accessKey"), Config.getSetting("aws.secretKey"));
        AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);

        // Configure region
        Region REGION = Region.getRegion(Regions.EU_WEST_1);
        client.setRegion(REGION);

        // Send email
        client.sendEmail(request);
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
