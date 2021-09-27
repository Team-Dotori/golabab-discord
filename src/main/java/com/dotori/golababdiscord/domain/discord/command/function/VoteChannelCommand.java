package com.dotori.golababdiscord.domain.discord.command.function;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.command.node.LeafCommand;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.service.VoteConfigurationService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class VoteChannelCommand extends LeafCommand {
    private final VoteConfigurationService voteConfigurationService;
    private final MessageSenderService messageSenderService;
    private final UserService userService;
    private final MessageViews messageViews;
    private final SogoBot sogoBot;

    public VoteChannelCommand(String prefix, VoteConfigurationService voteConfigurationService, UserService userService, MessageViews messageViews, MessageSenderService messageSenderService, SogoBot sogoBot) {
        super(prefix);
        this.voteConfigurationService = voteConfigurationService;
        this.userService = userService;
        this.messageViews = messageViews;
        this.messageSenderService = messageSenderService;
        this.sogoBot = sogoBot;
    }

    @Override
    protected void run(User user, MessageChannel channel, String args) {
        if(!checkPermission(user)) throw new PermissionDeniedException(Feature.GOLABAB_MANAGE);
        if(args.equals("변경")) {
            voteConfigurationService.changeChannel(channel);
            sendChannelChangedMessage(channel, user);
        } else if(args.equals("확인")) {
            voteConfigurationService.checkChannel(user);
            if(channel.getIdLong() != sogoBot.getVoteChannel().getIdLong())
                sendCheckChannelMessage(channel);
        }
    }

    private boolean checkPermission(User user) {
        SogoPermission permission = userService.getUserDto(user).getPermission();
        return permission.isHaveFeature(Feature.GOLABAB_MANAGE);
    }

    private void sendChannelChangedMessage(MessageChannel channel, User user) {
        ReceiverDto receiver = new ReceiverDto(channel);
        MessageDto message = messageViews.generateChannelChangedMessage(user);

        messageSenderService.sendMessage(receiver, message);
    }

    private void sendCheckChannelMessage(MessageChannel channel) {
        ReceiverDto receiver = new ReceiverDto(channel);
        MessageDto message = messageViews.generateCheckChannelAlarmMessage();

        messageSenderService.sendMessage(receiver, message);
    }
}
