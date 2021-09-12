package com.dotori.golababdiscord.domain.authorize.advice;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizeAdvice {
    @ExceptionHandler(UnsupportedJwtException.class)
    public String handleUnsupportedJwtException() {
        return "authorize/error/unsupported-jwt";
    }

    @ExceptionHandler({MalformedJwtException.class})
    public String handleMalformedJwtException() {
        return "authorize/error/malformed-jwt";
    }

    @ExceptionHandler({SignatureException.class})
    public String handleSignatureException() {
        return "authorize/error/expired-jwt";
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public String handleIllegalArgumentException() {
        return "authorize/error/token-not-found";
    }
}
