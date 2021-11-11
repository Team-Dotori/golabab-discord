package com.dotori.golababdiscord.domain.message;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.*;

public interface MessageFactory {
    //Command
    EmbedMessageDto generateWrongCommandUsageMessage(WrongCommandUsageType usageType, String args, String usage);
    EmbedMessageDto generateArgumentNotFoundMessage();

    //Authorize
    EmbedMessageDto generateRequestAuthorizeMessage();
    EmbedMessageDto generateMailSentMessage(DomainValidatedUserDto domainValidatedUser);
    EmbedMessageDto generateAuthorizeFailureMessage();
    EmbedMessageDto generateAuthorizeFailureMessage(FailureReason alreadyEnrolled);
    EmbedMessageDto generateAuthorizedMessage();
    //Vote
    EmbedMessageDto generateVoteOpenedMessage(VoteDto vote);
    EmbedMessageDto generateVoteClosedMessage();
    EmbedMessageDto generateAlreadyVoteMessage();
    //Tiptic
    EmbedMessageDto generateTipticMessage(String message);
    //Permission
    EmbedMessageDto generatePermissionDeniedMessage(SogoPermission permission, Feature feature);
    //VoteConfigure (toolbox)
    EmbedMessageDto generateChannelChangedMessage(UserDto user);
    EmbedMessageDto generateCheckChannelMessage(UserDto user);
    EmbedMessageDto generateCheckChannelAlarmMessage();//ex, 투표 채널에서 멘션을 보내드렸습니다!

    EmbedMessageDto generateRankingMessage(RequestRankingDto ranking);
}
