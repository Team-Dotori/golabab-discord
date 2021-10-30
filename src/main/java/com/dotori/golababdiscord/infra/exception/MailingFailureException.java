package com.dotori.golababdiscord.infra.exception;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public class MailingFailureException extends RuntimeException {
    public MailingFailureException(Throwable cause) {
        super(cause);
    }
}
