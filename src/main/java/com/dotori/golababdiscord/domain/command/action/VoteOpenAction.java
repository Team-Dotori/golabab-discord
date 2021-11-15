package com.dotori.golababdiscord.domain.command.action;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class VoteOpenAction {
    private final VoteScheduler voteScheduler;//투표 스케줄러
    private final UserService userService;
    private final SogoBot sogoBot;

    public void openVote(MealType type, UserDto user) {
        if(!checkPermission(user.getId())) throw new PermissionDeniedException(Feature.GOLABAB_MANAGE);

        switch (type) {
            case BREAKFAST -> voteScheduler.openBreakfastVote();
            case LUNCH -> voteScheduler.openLunchVote();
            case DINNER -> voteScheduler.openDinnerVote();
        }
    }

    //관리 권한이 있는지 확인하는 메소드
    private boolean checkPermission(long userId) {
        SogoPermission permission = userService.getUserDto(userId).getPermission();//유저의 권한을 가져옴
        return permission.isHaveFeature(Feature.GOLABAB_MANAGE);//관리 권한 소유 여부를 반환
    }
}
