package com.dotori.golababdiscord.domain.discord.view;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.*;
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

    static EmbedMessageDto getEmbedMessageByLegacyMessageDto(MessageDto legacyMessage) {
        EmbedMessageDto message = new EmbedMessageDto(
                -1,
                new TitleDto(legacyMessage.getTitle().getTitle(),
                        legacyMessage.getTitle().getUrl()),

                legacyMessage.getDescription(),

                legacyMessage.getColor(),

                new AuthorDto(legacyMessage.getAuthor().getName(),
                        legacyMessage.getAuthor().getUrl(),
                        legacyMessage.getAuthor().getIconUrl()),

                new FooterDto(legacyMessage.getFooter().getText(),
                        legacyMessage.getFooter().getIconUrl())

        );
        legacyMessage.getSections().forEach(section -> message.addSection(new SectionDto(section.getTitle(), section.getText(), section.getInline())));
        return message;
    }
}