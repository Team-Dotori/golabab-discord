package com.dotori.golababdiscord.domain.discord.listeners.handler;

import com.dotori.golababdiscord.domain.discord.exception.AlreadyVoteException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

@RequiredArgsConstructor
public class VoteHandler implements Handler<GuildMessageReactionAddEvent>{
    private final BotProperty botProperty;
    private final UserService userService;
    private final VoteService voteService;

    @Override
    public void handleEvent(GuildMessageReactionAddEvent event) {
        if(passed(event)) return;

        UserDto voter = userService.getUserDto(event.getUser());
        Message voteMessage = event.retrieveMessage().complete();

        if(!isVoterHavePermission(voter)) throw new PermissionDeniedException(Feature.GOLABAB_VOTE); //sendPermissionDeniedMessage(event);
        else if(checkIsAlreadyVote(voteMessage, voter)) throw new AlreadyVoteException(); //sendAlreadyVoteMessage(event);
    }

    private boolean isVoterHavePermission(UserDto voter) {
        return voter.getPermission().isHaveFeature(Feature.GOLABAB_VOTE);
    }

    private boolean passed(GuildMessageReactionAddEvent event) {
        if(event.getChannel().getIdLong() != botProperty.getVoteChannel()) return true; //투표 채널이 아니면 패스
        return !voteService.isVoteMessage(event.getMessageIdLong());
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
