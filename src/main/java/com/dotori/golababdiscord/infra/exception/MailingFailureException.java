package com.dotori.golababdiscord.infra.exception;

public class MailingFailureException extends RuntimeException {
    public MailingFailureException(Throwable cause) {
        super(cause);
    }
}
