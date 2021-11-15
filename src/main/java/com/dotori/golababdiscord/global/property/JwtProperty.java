package com.dotori.golababdiscord.global.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Configuration
@ConfigurationProperties("jwt")
@Getter @Setter
public class JwtProperty {
    private String secret;
    private String issuer;
}
