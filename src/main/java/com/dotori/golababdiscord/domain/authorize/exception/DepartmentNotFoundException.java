package com.dotori.golababdiscord.domain.authorize.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//소속명을 찾을 수 없을경우 발생하는 예외
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DepartmentNotFoundException extends RuntimeException {
    private String domain; //찾으려는 소속명
}
