package com.dotori.golababdiscord.infra.service;

import com.dotori.golababdiscord.infra.exception.MailingFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service("jms")
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendHtmlEmail(String to, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(title);
            message.setText(content, StandardCharsets.UTF_8.name(), "html");
        } catch (MessagingException e) {
            throw new MailingFailureException(e);
        }
        mailSender.send(message);
    }
}
