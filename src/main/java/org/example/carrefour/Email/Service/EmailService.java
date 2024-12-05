package org.example.carrefour.Email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.carrefour.Email.Config.EmailConfig;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final Session emailSession;
    private final EmailConfig emailConfig;

    @Autowired
    public EmailService(Session emailSession, EmailConfig emailConfig) {
        this.emailSession = emailSession;
        this.emailConfig = emailConfig;
    }

    public boolean sendEmail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(this.emailSession);
            message.setFrom(new InternetAddress(emailConfig.getUser()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}