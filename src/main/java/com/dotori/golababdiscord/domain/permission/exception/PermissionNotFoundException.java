package com.dotori.golababdiscord.domain.permission.exception;

import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PermissionNotFoundException extends RuntimeException {
    private final Feature feature;
}
