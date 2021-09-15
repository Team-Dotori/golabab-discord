package com.dotori.golababdiscord.domain.discord.dto;

import lombok.Getter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageDto {
    @Getter private final TitleDto title;
    @Getter private final String description;
    @Getter private final Color color;
    @Getter private final AuthorDto author;
    @Getter private final FooterDto footer;
    @Getter private final List<SectionDto> sections;
    @Getter private final List<String> emojis;

    public MessageDto(TitleDto title,
                      String description,
                      Color color,
                      AuthorDto author,
                      FooterDto footer) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.sections = new ArrayList<>();
        this.author = author;
        this.footer = footer;
        this.emojis = new ArrayList<>();
    }

    public void addSection(SectionDto section) {
        sections.add(section);
    }

    public void addSections(SectionDto... section) {
        sections.addAll(Arrays.asList(section));
    }

    public void addEmoji(String emoji) {
        emojis.add(emoji);
    }
}
