package com.dotori.golababdiscord.domain.discord.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SectionDto {
    private final String title;
    private final String text;
    private final Boolean inline;
}
