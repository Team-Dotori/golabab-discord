package com.dotori.golababdiscord.domain.discord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class AuthorDto {
    private final String name;
    private String url;
    private String iconUrl;
}
