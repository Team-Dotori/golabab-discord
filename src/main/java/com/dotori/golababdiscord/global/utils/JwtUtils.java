package com.dotori.golababdiscord.global.utils;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface JwtUtils<T1, T2> {
    String generateToken(T1 target);
    void validateToken(String token);
    T2 decodeToken(String token);
}
