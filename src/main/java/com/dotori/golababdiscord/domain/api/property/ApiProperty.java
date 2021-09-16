package com.dotori.golababdiscord.domain.api.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("vote-api")
@Getter @Setter
public class ApiProperty {
    private String baseUrl;
    private String port;
}
