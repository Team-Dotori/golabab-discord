package com.dotori.golababdiscord.infra.service;

import org.springframework.stereotype.Service;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Service
public interface MailService {
    void sendHtmlEmail(String to, String title, String content);
}
