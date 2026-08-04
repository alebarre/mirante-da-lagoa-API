package br.com.mirantedalagoa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Mirante da Lagoa - Código de recuperação de senha");
        message.setText("Seu código de recuperação de senha é: " + code + "\n\n" +
            "Este código expira em 15 minutos.\n" +
            "Se você não solicitou a recuperação, ignore este e-mail.");
        mailSender.send(message);
    }
}
