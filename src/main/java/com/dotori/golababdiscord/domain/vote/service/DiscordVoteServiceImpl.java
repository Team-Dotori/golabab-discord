package com.dotori.golababdiscord.domain.vote.service;

import com.dotori.golababdiscord.domain.api.service.LunchApiService;
import com.dotori.golababdiscord.domain.api.service.VoteApiService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import com.dotori.golababdiscord.domain.vote.repository.InProgressVoteRepository;
import com.dotori.golababdiscord.domain.vote.repository.MenuRepository;
import com.dotori.golababdiscord.global.utils.DateUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DiscordVoteServiceImpl extends VoteServiceImpl{
    @Lazy
    public DiscordVoteServiceImpl(LunchApiService lunchApiService,
                                  VoteApiService voteApiService,
                                  MessageSenderService messageSenderService,
                                  MessageViews messageViews,
                                  InProgressVoteRepository inProgressVoteRepository,
                                  MenuRepository menuRepository,
                                  DateUtils dateUtils,
                                  SogoBot sogoBot) {
        super(lunchApiService, voteApiService, messageSenderService, messageViews, inProgressVoteRepository, menuRepository, dateUtils, sogoBot);
    }

    @Override
    protected Long sendVoteMessageAndGetId(VoteDto vote) {
        TextChannel channel = super.sogoBot.getVoteChannel();

        MessageDto message = super.messageViews.generateVoteOpenedMessage(vote);
        ReceiverDto receiver = new ReceiverDto(channel);

        return super.messageSenderService.sendMessage(receiver, message);
    }
}
