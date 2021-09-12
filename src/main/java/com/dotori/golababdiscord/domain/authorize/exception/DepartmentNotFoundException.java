package com.dotori.golababdiscord.domain.authorize.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DepartmentNotFoundException extends RuntimeException {
    private String domain;
}
