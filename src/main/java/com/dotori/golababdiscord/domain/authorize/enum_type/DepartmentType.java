package com.dotori.golababdiscord.domain.authorize.enum_type;

import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum DepartmentType {
    GWANGJU("gsm.hs.kr");

    private final String domain;

    public static DepartmentType of(String name) {
        for (DepartmentType value : values()) {
            if(name.equals(value.name())) return value;
        } throw new DepartmentNotFoundException();
    }
}
