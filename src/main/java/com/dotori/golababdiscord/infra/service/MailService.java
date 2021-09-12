package com.dotori.golababdiscord.infra.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendHtmlEmail(String to, String title, String content);
}
