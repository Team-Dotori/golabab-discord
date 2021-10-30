package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface MessageViews {
    //Command
    MessageDto generateWrongCommandUsageMessage(WrongCommandUsageType usageType, String args, String usage);
    MessageDto generateArgumentNotFoundMessage();

    //Authorize
    MessageDto generateRequestAuthorizeMessage();
    MessageDto generateMailSentMessage(DomainValidatedUserDto domainValidatedUser);
    MessageDto generateAuthorizeFailureMessage();
    MessageDto generateAuthorizeFailureMessage(FailureReason alreadyEnrolled);
    MessageDto generateAuthorizedMessage();
    //Vote
    MessageDto generateVoteOpenedMessage(VoteDto vote);
    MessageDto generateVoteClosedMessage();
    MessageDto generateAlreadyVoteMessage();
    //Tiptic
    MessageDto generateTipticMessage(String message);
    //Permission
    MessageDto generatePermissionDeniedMessage(SogoPermission permission, Feature feature);
    //VoteConfigure (toolbox)
    MessageDto generateChannelChangedMessage(User user);
    MessageDto generateCheckChannelMessage(User user);
    MessageDto generateCheckChannelAlarmMessage();//ex, 투표 채널에서 멘션을 보내드렸습니다!

    MessageDto generateRankingMessage(RequestRankingDto ranking);
}