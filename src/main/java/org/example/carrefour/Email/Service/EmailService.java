package org.example.carrefour.Email.Service;

import org.example.carrefour.Email.Jwt.ConfirmationTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.carrefour.Email.Config.EmailConfig;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {
    @Autowired
    Session emailSession;
    @Autowired
    EmailConfig emailConfig;
    @Autowired
    ConfirmationTokenProvider confirmationTokenProvider;


    public boolean sendEmail(String to, String subject) {
        try {
            // Generar el token de confirmación
            String token = this.confirmationTokenProvider.generateConfirmationToken(to);
            System.out.println(token);

            // Hardcodear el contenido HTML dentro de la función
            String htmlContent = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Confirm Your Account</title>
                        <style>
                            body {
                                font-family: 'Arial', sans-serif;
                                line-height: 1.6;
                                background-color: #f4f4f4;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                                margin: 0;
                                padding: 20px;
                                box-sizing: border-box;
                            }
                    
                            .confirmation-container {
                                background-color: white;
                                border-radius: 10px;
                                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                                max-width: 500px;
                                width: 100%;
                                padding: 40px;
                                text-align: center;
                            }
                    
                            .logo {
                                margin-bottom: 20px;
                                width: 100px;
                                height: 100px;
                                background-color: #007bff;
                                border-radius: 50%;
                                display: inline-block;
                            }
                    
                            h1 {
                                color: #333;
                                margin-bottom: 20px;
                            }
                    
                            .verification-text {
                                color: #666;
                                margin-bottom: 30px;
                            }
                    
                            .confirm-button {
                                display: inline-block;
                                background-color: #28a745;
                                color: white;
                                text-decoration: none;
                                padding: 12px 24px;
                                border-radius: 5px;
                                font-weight: bold;
                                transition: background-color 0.3s ease;
                            }
                    
                            .confirm-button:hover {
                                background-color: #218838;
                            }
                    
                            .expiry-text {
                                color: #6c757d;
                                font-size: 0.9em;
                                margin-top: 20px;
                            }
                        </style>
                    </head>
                    <body>
                    <div class="confirmation-container">
                        <div class="logo"></div>
                        <h1>Confirm Your Account</h1>
                        <p class="verification-text">
                            Thank you for signing up! Please click the button below to verify your email address and activate your account.
                        </p>
                        <a href="http://example.com/confirm?token=%s" class="confirm-button">Verify Email Address</a>
                        <p class="expiry-text">
                            This link will expire in 24 hours. If you did not create an account, please ignore this email.
                        </p>
                    </div>
                    </body>
                    </html>
                    """;

            // Reemplazar %s con el token en el contenido HTML
            String personalizedHtmlContent = htmlContent.replace("%s", token);

            // Crear y enviar el mensaje de email
            Message message = new MimeMessage(this.emailSession);
            message.setFrom(new InternetAddress(this.emailConfig.getUser()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Configurar el contenido del mensaje
            message.setContent(personalizedHtmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private String readHtmlFile(String filePath) throws Exception {
        return new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
    }


}