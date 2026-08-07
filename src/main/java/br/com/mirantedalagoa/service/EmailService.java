package br.com.mirantedalagoa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from:}")
    private String fromAddress;

    public void sendPasswordResetCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        if (fromAddress != null && !fromAddress.isBlank()) {
            message.setFrom(fromAddress);
        }
        message.setTo(to);
        message.setSubject("Mirante da Lagoa - Código de recuperação de senha");
        message.setText("Seu código de recuperação de senha é: " + code + "\n\n" +
            "Este código expira em 15 minutos.\n" +
            "Se você não solicitou a recuperação, ignore este e-mail.");
        mailSender.send(message);
        logger.info("Código de recuperação enviado para {}", to);
    }
}