package com.dotori.golababdiscord.domain.authorize.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class AuthorizeAdvice {
    @ExceptionHandler(UnsupportedJwtException.class)
    public String handleUnsupportedJwtException() {
        return "authorize/error/unsupported-jwt";
    }

    @ExceptionHandler(MalformedJwtException.class)
    public String handleMalformedJwtException() {
        return "authorize/error/malformed-jwt";
    }

    @ExceptionHandler(SignatureException.class)
    public String handleSignatureException() {
        return "authorize/error/unknown-signature";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public String handleExpiredJwtException() {
        return "authorize/error/expired-jwt";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException() {
        return "authorize/error/token-not-found";
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String handleSQLException() {
        return "enroll/error/already-enrolled";
    }
}
