package com.dotori.golababdiscord.domain.permission.exception;

import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class PermissionNotFoundException extends RuntimeException {
    private final Feature feature;
}
