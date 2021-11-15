package com.dotori.golababdiscord.domain.authorize.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//검증이 완료된 유저의 정보를 담는 DTO
@RequiredArgsConstructor
@Getter
public class ValidatedUserDto {
    private final Long discordId;//해당 유저의 DiscordID
    private final String name;//해당 유저의 이름
    private final String email;//해당 유저의 이메일
    private final DepartmentType department;//해당 유저의 소속정보

    //검증이 완료된 유저를 등록된 유저정보를 담는 UserDto 로 치환한다
    public UserDto toUserDto() {
        return new UserDto(this.discordId, this.name, this.email, this.department, SogoPermission.STUDENT);
    }
}
