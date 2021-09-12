package com.dotori.golababdiscord.domain.discord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class TitleDto {
    private final String title;
    private String url;
}
