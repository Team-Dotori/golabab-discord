package com.dotori.golababdiscord.domain.discord.listeners;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import com.dotori.golababdiscord.global.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Slf4j
public class VoteListener extends ListenerAdapter {
    private final BotProperty botProperty;
    private final UserService userService;
    private final VoteService voteService;
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if(passed(event)) return;

        UserDto voter = userService.getUser(event.getUser());
        Message voteMessage = event.retrieveMessage().complete();

        if(checkIsAlreadyVote(voteMessage, voter)) {
            cancelEvent(event);
            sendAlreadyVoteMessage(event);
        }
    }

    private boolean passed(GuildMessageReactionAddEvent event) {
        if(event.retrieveUser().complete().isBot()) return true; //유저가 아니라 봇이면 패스
        if(event.getChannel().getIdLong() != botProperty.getVoteChannel()) return true; //투표 채널이 아니면 패스
        return !voteService.isVoteMessage(event.getMessageIdLong());
    }

    private void sendAlreadyVoteMessage(@NotNull GuildMessageReactionAddEvent event) {
        MessageChannel privateChannel = event.retrieveUser().complete().openPrivateChannel().complete();

        MessageDto message = messageViews.generateAlreadyVoteMessage();
        ReceiverDto receiver = new ReceiverDto(privateChannel);

        messageSenderService.sendMessage(receiver, message);
    }

    private void cancelEvent(@NotNull GuildMessageReactionAddEvent event) {
        event.getReaction().removeReaction(
                event.retrieveUser().complete()
        ).complete();
    }

    private boolean checkIsAlreadyVote(Message message, UserDto user) {
        //추가된 이모지까지 합산해서 체크하므로, vote이모지가 2개 이상이 되어야지 중복투표가 된다
        long voteCount = message.getReactions().stream().filter(reaction ->
                VoteEmoji.isVoteEmoji(reaction) && isReactor(reaction, user.getDiscordId())).count();
        return voteCount > 1;
    }

    private boolean isReactor(MessageReaction reaction, Long userId) {
        return reaction.retrieveUsers().complete().stream()
                .map(ISnowflake::getIdLong)
                .anyMatch(id -> id.equals(userId));
    }
}
