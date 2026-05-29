package co.edu.unbosque.horaclass.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${app.mail.from:noreply@horaclass.unbosque.edu.co}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean isConfigured() {
        return mailUsername != null && !mailUsername.isBlank();
    }

    public void sendPasswordResetEmail(String to, String resetLink, String username) {
        if (!isConfigured()) {
            logger.warn("Mailtrap no configurado. Enlace de recuperación para {}: {}", username, resetLink);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject("HoraClass — Restablecer contraseña");
        message.setText("""
                Hola %s,

                Recibimos una solicitud para restablecer tu contraseña en HoraClass.

                Usa el siguiente enlace (válido por 1 hora):
                %s

                Si no solicitaste este cambio, ignora este correo.

                Universidad El Bosque — HoraClass
                """.formatted(username, resetLink));

        mailSender.send(message);
        logger.info("Correo de recuperación enviado a {}", to);
    }
}
