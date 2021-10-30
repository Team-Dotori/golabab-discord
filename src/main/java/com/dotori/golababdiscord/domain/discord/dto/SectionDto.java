package com.dotori.golababdiscord.domain.discord.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class SectionDto {
    private final String title;
    private final String text;
    private final Boolean inline;
}
