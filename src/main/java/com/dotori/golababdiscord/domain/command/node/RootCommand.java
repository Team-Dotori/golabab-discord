package com.dotori.golababdiscord.domain.command.node;

import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.command.function.AuthorizeCommand;
import com.dotori.golababdiscord.domain.command.function.VoteChannelCommand;
import com.dotori.golababdiscord.domain.command.function.VoteCommand;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import com.dotori.golababdiscord.domain.vote.service.VoteConfigurationService;
import com.dotori.golababdiscord.infra.service.MailService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
@Slf4j
public class RootCommand extends Command{
    private final BotProperty botProperty;
    public RootCommand(BotProperty botProperty,
                       MailService mailService,
                       MessageSenderService messageSenderService,
                       AuthorizeService authorizeService,
                       MessageViews messageViews,
                       SpringTemplateEngine templateEngine,
                       VoteScheduler voteScheduler,
                       VoteConfigurationService voteConfigurationService,
                       UserService userService,
                       SogoBot sogoBot) {
        super(botProperty.getCommandPrefix());
        initChildren(mailService, messageSenderService, authorizeService, messageViews, templateEngine, voteScheduler, voteConfigurationService, userService, sogoBot);
        initDepth(0);
        this.botProperty = botProperty;
    }

    private void initChildren(MailService mailService,
                              MessageSenderService messageSenderService,
                              AuthorizeService authorizeService,
                              MessageViews messageViews,
                              SpringTemplateEngine templateEngine,
                              VoteScheduler voteScheduler,
                              VoteConfigurationService voteConfigurationService,
                              UserService userService,
                              SogoBot sogoBot) {
        AuthorizeCommand authorizeCommand =
                new AuthorizeCommand("인증", mailService, messageSenderService, authorizeService, messageViews, templateEngine);
        VoteCommand voteCommand =
                new VoteCommand("투표", voteScheduler);
        VoteChannelCommand voteChannelCommand =
                new VoteChannelCommand("채널", voteConfigurationService, userService, messageViews, messageSenderService, sogoBot);

        this.addChild(authorizeCommand);
        this.addChild(voteCommand);
        voteCommand.addChild(voteChannelCommand);
    }

    @Override
    protected void run(User user, MessageChannel channel, String args) {
        throw new WrongArgumentException(args, "존재하지 않는 명령어입니다!");
    }

    @Override
    public void execute(User user, MessageChannel channel, String args) {
        String prefix = getRootInputPrefix(args);
        String childArgs = encodeRootArgsByInput(args);
        if(commandTrigger.checkTrigger(prefix)) {
            super.execute(user, channel, childArgs);
        }
    }

    public void executeRoot(User user, MessageChannel channel, String args) {
        execute(user, channel, args);
    }

    private String encodeRootArgsByInput(String args) {
        if(!args.contains(" ")) return "";
        return args.substring(args.indexOf(" ") + 1);
    }

    private String getRootInputPrefix(String args) {
        return args.split(" ")[0];
    }
}
