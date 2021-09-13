package com.dotori.golababdiscord.domain.discord.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("bot")
@Getter @Setter
public class BotProperty {
    private String commandPrefix;
    private String token;
}
