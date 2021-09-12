package com.dotori.golababdiscord.global.utils;

public interface JwtUtils<T1, T2> {
    String generateToken(T1 target);
    void validateToken(String token);
    T2 decodeToken(String token);
}
