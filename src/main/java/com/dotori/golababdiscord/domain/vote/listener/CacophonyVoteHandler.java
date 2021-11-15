package com.dotori.golababdiscord.domain.vote.listener;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.exception.AlreadyVoteException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.enum_type.VoteEmoji;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import io.github.key_del_jeeinho.cacophony_lib.domain.event.events.react.ReactEvent;
import io.github.key_del_jeeinho.cacophony_lib.domain.event.listeners.EventListener;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.entry.EntryEntry.onReact;
import static io.github.key_del_jeeinho.cacophony_lib.domain.flow.FlowEntry.when;

@Component
public class CacophonyVoteHandler {
    private final BotProperty botProperty;
    private final UserService userService;
    private final VoteService voteService;
    private final SogoBot sogoBot;

    public CacophonyVoteHandler(BotProperty botProperty, UserService userService, VoteService voteService, SogoBot sogoBot) {
        this.botProperty = botProperty;
        this.userService = userService;
        this.voteService = voteService;
        this.sogoBot = sogoBot;

        init();
    }

    private void init() {
        when(onReact()).doAction((EventListener<ReactEvent>) this::handleEvent);
    }

    private void handleEvent(ReactEvent event) {
        checkIsUserVote(event);

        UserDto voter = userService.getUserDto(event.getReactor().getId());
        long messageId = event.getMessage().getId();

        if(!checkVoterPermission(voter)) throw new PermissionDeniedException(Feature.GOLABAB_VOTE);
        else if(checkIsAlreadyVote(messageId, voter)) throw new AlreadyVoteException();
    }

    private boolean checkIsAlreadyVote(long messageId, UserDto voter) {
        //추가된 이모지까지 합산해서 체크하므로, vote이모지가 2개 이상이 되어야지 중복투표가 된다
        Message message = sogoBot.getVoteMessageById(messageId);
        long voteCount = message.getReactions().stream().filter(reaction ->
                VoteEmoji.isVoteEmoji(reaction) && isReactor(reaction, voter.getDiscordId())).count();
        return voteCount > 1;
    }

    private boolean checkVoterPermission(UserDto voter) {
        return voter.getPermission().isHaveFeature(Feature.GOLABAB_VOTE);
    }

    private boolean checkIsUserVote(ReactEvent event) {
        return event.getEventType().equals(ReactEvent.EventType.ADD) &&
                sogoBot.getVoteChannelId() == event.getChannel().getId() &&
                voteService.isVoteMessage(event.getMessage().getId());
    }

    private boolean isReactor(MessageReaction reaction, Long userId) {
        return reaction.retrieveUsers().complete().stream()
                .map(ISnowflake::getIdLong)
                .anyMatch(id -> id.equals(userId));
    }
}
