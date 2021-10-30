package com.dotori.golababdiscord.domain.authorize.enum_type;

import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//학교 소속 정보를 담는 Enum 타입
@RequiredArgsConstructor @Getter
public enum DepartmentType {
    GWANGJU("gsm.hs.kr");//광주 SW 마이스터고등학교 소속

    private final String domain;//해당 소속의 이메일 도메인

    //소속명을 통해 소속 정보를 반환하는 메소드
    public static DepartmentType of(String name) {
        for (DepartmentType value : values()) {//저장된 모든 소속정보를 불러와서 하나씩 Iteration 한다
            if(name.equals(value.name())) return value;//소속명이 일치하면 해당 소속 정보를 반환한다
        } throw new DepartmentNotFoundException();//소속 정보를 찾을 수 없을 때 DepartmentNotFoundException 을 발생시킨다
    }
}
