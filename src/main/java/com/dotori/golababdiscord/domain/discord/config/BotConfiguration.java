package com.dotori.golababdiscord.domain.discord.config;

import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.listeners.handler.CommandHandler;
import com.dotori.golababdiscord.domain.discord.command.node.RootCommand;
import com.dotori.golababdiscord.domain.discord.command.function.AuthorizeCommand;
import com.dotori.golababdiscord.domain.discord.command.function.VoteChannelCommand;
import com.dotori.golababdiscord.domain.discord.command.function.VoteCommand;
import com.dotori.golababdiscord.domain.discord.listeners.CommandListener;
import com.dotori.golababdiscord.domain.discord.listeners.VoteListener;
import com.dotori.golababdiscord.domain.discord.listeners.handler.VoteHandler;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import com.dotori.golababdiscord.domain.vote.service.VoteConfigurationService;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import com.dotori.golababdiscord.infra.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
@RequiredArgsConstructor
public class BotConfiguration {
    private final BotProperty botProperty;
    private final AuthorizeService authorizeService;
    private final MailService mailService;
    private final MessageSenderService messageSenderService;
    private final VoteConfigurationService voteConfigurationService;
    private final SpringTemplateEngine templateEngine;
    private final MessageViews messageViews;
    private final VoteScheduler voteScheduler;
    private final VoteService voteService;
    private final UserService userService;
    private final SogoBot sogoBot;

    private static VoteListener voteListener;
    private static VoteHandler voteHandler;
    private static CommandListener commandListener;
    private static CommandHandler commandHandler;
    private static RootCommand rootCommand;

    @Bean
    public VoteListener voteListener() {
        if(voteListener == null) {
            voteListener = new VoteListener(voteHandler());
        }

        sogoBot.addEventListener(voteListener);
        return voteListener;
    }

    @Bean
    public VoteHandler voteHandler() {
        if(voteHandler == null)
            voteHandler = new VoteHandler(botProperty, userService, voteService);
        return voteHandler;
    }

    @Bean
    public CommandListener commandListener() {
        if(commandListener == null)
            commandListener = new CommandListener(commandHandler());

        sogoBot.addEventListener(commandListener);
        return commandListener;
    }

    @Bean
    public CommandHandler commandHandler() {
        if(commandHandler == null)
            commandHandler = new CommandHandler(rootCommand());

        return commandHandler;
    }

    @Bean
    public RootCommand rootCommand() {
        if(rootCommand == null) {
            rootCommand = new RootCommand(botProperty.getCommandPrefix());

            AuthorizeCommand authorizeCommand = new AuthorizeCommand("인증", mailService, messageSenderService, authorizeService, messageViews, templateEngine);
            rootCommand.addChild(authorizeCommand);

            VoteCommand voteCommand = new VoteCommand("투표", voteScheduler);
            rootCommand.addChild(voteCommand);
            VoteChannelCommand voteChannelCommand = new VoteChannelCommand("채널", voteConfigurationService, userService, messageViews, messageSenderService, sogoBot);
            voteCommand.addChild(voteChannelCommand);
        }
        return rootCommand;
    }
}