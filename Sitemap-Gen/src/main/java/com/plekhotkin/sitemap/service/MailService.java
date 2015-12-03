package com.plekhotkin.sitemap.service;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.plekhotkin.sitemap.util.ApplicationProperties;


public class MailService {

    public MailService() {
        ApplicationProperties.getProperty("mail.from");
        ApplicationProperties.getProperty("mail.username");
        ApplicationProperties.getProperty("mail.password");
        ApplicationProperties.getProperty("mail.smtp.auth");
        ApplicationProperties.getProperty("mail.smtp.starttls.enable");
        ApplicationProperties.getProperty("mail.smtp.host");
        ApplicationProperties.getProperty("mail.smtp.port");
        ApplicationProperties.getProperty("mail.password");
    }

    private boolean validateEmail(String email) {
        // TODO: validate email
        throw new UnsupportedOperationException();
    }

    public boolean sendSitemap(String to, String subject, String messageText, String attachmentPath) {

        // Sender's email ID needs to be mentioned
        String from = ApplicationProperties.getProperty("mail.from");

        final String username = ApplicationProperties.getProperty("mail.username");
        final String password = ApplicationProperties.getProperty("mail.password");
        
        String host = ApplicationProperties.getProperty("mail.smtp.host");

        Properties props = new Properties();
        
        props.put("mail.smtp.starttls.enable", ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.socketFactory.port",  ApplicationProperties.getProperty("mail.smtp.socketFactory.port"));
        props.setProperty("mail.smtp.socketFactory.class", ApplicationProperties.getProperty("mail.smtp.socketFactory.class"));
        props.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
        props.setProperty("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
        
        // Get the Session object.
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(messageText);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentPath.substring(attachmentPath.lastIndexOf("/")+1));
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
