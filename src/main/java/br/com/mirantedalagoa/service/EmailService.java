package br.com.mirantedalagoa.service;

import jakarta.activation.URLDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${spring.mail.from:}")
    private String fromAddress;

    public void sendPasswordResetCode(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            if (fromAddress != null && !fromAddress.isBlank()) {
                helper.setFrom(fromAddress);
            }
            helper.setTo(to);
            helper.setSubject("Mirante da Lagoa - Código de recuperação de senha");
            helper.setText(buildPasswordResetHtml(code), true);

            Resource image = resourceLoader.getResource("classpath:static/images/coqueiro-transparente.png");
            if (image.exists()) {
                helper.addInline("coqueiroLogo", new URLDataSource(image.getURL()));
            }

            mailSender.send(message);
            logger.info("Código de recuperação enviado para {}", to);
        } catch (Exception e) {
            logger.error("Falha ao enviar e-mail HTML de recuperação: {}", e.getMessage());
            fallbackSendPasswordResetCode(to, code);
        }
    }

    private void fallbackSendPasswordResetCode(String to, String code) {
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
    }

    private String buildPasswordResetHtml(String code) {
        return "<!DOCTYPE html>" +
            "<html lang=\"pt-BR\">" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
            "<title>Recuperação de Senha - Mirante da Lagoa</title>" +
            "</head>" +
            "<body style=\"margin:0;padding:0;background:#f4f6f9;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;\">" +
            "<table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">" +
            "<tr>" +
            "<td align=\"center\" style=\"padding:40px 20px;background:linear-gradient(135deg,#1e3c72 0%,#27ae60 100%);\">" +
            "<table role=\"presentation\" width=\"480\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"max-width:480px;width:100%;\">" +
            "<tr>" +
            "<td style=\"background:#ffffff;border-radius:14px;box-shadow:0 10px 30px rgba(0,0,0,0.12);padding:36px;text-align:center;\">" +
            "<img src=\"cid:coqueiroLogo\" alt=\"Mirante da Lagoa\" style=\"width:120px;height:auto;margin-bottom:12px;filter:drop-shadow(0 4px 8px rgba(0,0,0,0.15));\" />" +
            "<h1 style=\"margin:0 0 6px;color:#1e3c72;font-size:26px;font-weight:600;\">Mirante da Lagoa</h1>" +
            "<p style=\"margin:0 0 24px;color:#6c757d;font-size:14px;\">Saquarema/RJ - Acesso ao sistema</p>" +
            "<h2 style=\"margin:0 0 12px;color:#212529;font-size:18px;font-weight:600;\">Recuperação de Senha</h2>" +
            "<p style=\"margin:0 0 24px;color:#495057;font-size:15px;line-height:1.6;\">" +
            "Você solicitou a recuperação de senha. Utilize o código abaixo para redefinir sua senha:" +
            "</p>" +
            "<div style=\"display:inline-block;background:#1e3c72;color:#ffffff;font-size:32px;font-weight:700;letter-spacing:4px;padding:16px 32px;border-radius:10px;margin-bottom:24px;\">" +
            code +
            "</div>" +
            "<p style=\"margin:0 0 16px;color:#495057;font-size:14px;\">" +
            "Este código expira em <strong style=\"color:#1e3c72;\">15 minutos</strong>." +
            "</p>" +
            "<p style=\"margin:0;color:#6c757d;font-size:13px;line-height:1.5;\">" +
            "Se você não solicitou a recuperação, ignore este e-mail." +
            "<br>Nenhuma alteração será feita em sua conta." +
            "</p>" +
            "</td>" +
            "</tr>" +
            "</table>" +
            "</td>" +
            "</tr>" +
            "</table>" +
            "</body>" +
            "</html>";
    }
}