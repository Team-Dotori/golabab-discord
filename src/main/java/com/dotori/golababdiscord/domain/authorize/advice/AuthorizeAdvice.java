package com.dotori.golababdiscord.domain.authorize.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

//authorize 단에서 발생하는 예외들을 처리하는 클래스
@ControllerAdvice
public class AuthorizeAdvice {
    @ExceptionHandler(UnsupportedJwtException.class) //지원하지 않는 JWT 형식일 경우
    public String handleUnsupportedJwtException() {
        return "authorize/error/unsupported-jwt";
    }

    @ExceptionHandler(MalformedJwtException.class) //JWT 구조가 잘못되었을경우
    public String handleMalformedJwtException() {
        return "authorize/error/malformed-jwt";
    }

    @ExceptionHandler(SignatureException.class) //JWT 의 서명을 찾을 수 없을경우
    public String handleSignatureException() {
        return "authorize/error/unknown-signature";
    }

    @ExceptionHandler(ExpiredJwtException.class) //JWT 가 만료되었을경우
    public String handleExpiredJwtException() {
        return "authorize/error/expired-jwt";
    }

    @ExceptionHandler(IllegalArgumentException.class) //JWT 토큰이 비어있을경우
    public String handleIllegalArgumentException() {
        return "authorize/error/token-not-found";
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) //이미 가입하였을경우(PK 가 Duplicated 되었을경우)
    public String handleSQLException() {
        return "enroll/error/already-enrolled";
    }
}
