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

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpleemail.AWSJavaMailTransport;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;
import es.logongas.fpempresa.modelo.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servicio de envio de EMails.
 *
 * @author logongas
 */
public class MailServiceImplAWS implements MailService {

    @Override
    public void send(Mail mail) throws IOException {
        /*
         * Important: Be sure to fill in your AWS access credentials in the
         * AwsCredentials.properties file before you try to run this sample.
         * http://aws.amazon.com/security-credentials
         */
        PropertiesCredentials credentials = new PropertiesCredentials(
                MailServiceImplAWS.class
                .getResourceAsStream("/AwsCredentials.properties"));
        AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(credentials);

        /*
         * Before you can send email via Amazon SES, you need to verify that you
         * own the email address from which youÕll be sending email. This will
         * trigger a verification email, which will contain a link that you can
         * click on to complete the verification process.
         */

        /*
         * If you've just signed up for SES, then you'll be placed in the Amazon
         * SES sandbox, where you must also verify the email addresses you want
         * to send mail to.
         *
         * You can uncomment the line below to verify the TO address in this
         * sample.
         *
         * Once you have full access to Amazon SES, you will *not* be required
         * to verify each email address you want to send mail to.
         *
         * You can request full access to Amazon SES here:
         * http://aws.amazon.com/ses/fullaccessrequest
         */
//        verifyEmailAddress(mail.getFrom());
        
        /*
		 * Setup JavaMail to use the Amazon Simple Email Service by specifying
		 * the "aws" protocol.
         */
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "aws");

        /*
         * Setting mail.aws.user and mail.aws.password are optional. Setting
         * these will allow you to send mail using the static transport send()
         * convince method.  It will also allow you to call connect() with no
         * parameters. Otherwise, a user name and password must be specified
         * in connect.
         */
        props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
        props.setProperty("mail.aws.password", credentials.getAWSSecretKey());

        Session session = Session.getInstance(props);

        try {
            // Create a new Message
            Message msg = new MimeMessage(session);
            msg.setFrom(mail.getFrom());
            for (InternetAddress internetAddress : mail.getTo()) {
                msg.addRecipient(Message.RecipientType.TO, internetAddress);
            }
            msg.setSubject(mail.getSubject());
            msg.setText(mail.getBody());
            msg.saveChanges();

            // Reuse one Transport object for sending all your messages
            // for better performance
            Transport t = new AWSJavaMailTransport(session, null);
            t.connect();
            t.sendMessage(msg, null);

            // Close your transport when you're completely done sending
            // all your messages
            t.close();
        } catch (AddressException e) {
            e.printStackTrace();
            System.out.println("Caught an AddressException, which means one or more of your "
                    + "addresses are improperly formatted.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Caught a MessagingException, which means that there was a "
                    + "problem sending your message to Amazon's E-mail Service check the "
                    + "stack trace for more information.");
        }
    }

    @Override
    public void verifyEmailAddress(InternetAddress internetAddress) throws IOException{
        
        PropertiesCredentials credentials = new PropertiesCredentials(
                MailServiceImplAWS.class
                .getResourceAsStream("/AwsCredentials.properties"));
        AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(credentials);
        
        ListVerifiedEmailAddressesResult verifiedEmails = ses.listVerifiedEmailAddresses();
        if (verifiedEmails.getVerifiedEmailAddresses().contains(internetAddress.getAddress())) {
            return;
        }

        ses.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(internetAddress.getAddress()));
        System.out.println("Please check the email address " + internetAddress.getAddress() + " to verify it");
        System.exit(0);
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
