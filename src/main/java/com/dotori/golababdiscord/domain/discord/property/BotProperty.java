package com.dotori.golababdiscord.domain.discord.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Configuration
@ConfigurationProperties("bot")
@Getter @Setter
public class BotProperty {
    private String commandPrefix;
    private String token;
    private Long voteChannel;
}
