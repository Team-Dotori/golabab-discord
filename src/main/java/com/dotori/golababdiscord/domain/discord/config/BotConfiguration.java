package com.dotori.golababdiscord.domain.discord.config;

import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.command.RootCommand;
import com.dotori.golababdiscord.domain.discord.command.function.AuthorizeCommand;
import com.dotori.golababdiscord.domain.discord.command.function.VoteCommand;
import com.dotori.golababdiscord.domain.discord.listeners.CommandListener;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
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
    private final SpringTemplateEngine templateEngine;
    private final MessageViews messageViews;
    private final VoteScheduler voteScheduler;

    private static SogoBot sogoBot;
    private static CommandListener commandListener;
    private static RootCommand rootCommand;

    @Bean
    public SogoBot sogoBot() {
        if(sogoBot == null) {
            sogoBot = new SogoBot(botProperty);
            sogoBot().addEventListener(commandListener());
        } return sogoBot;
    }

    @Bean
    public CommandListener commandListener() {
        if(commandListener == null)
            commandListener = new CommandListener(rootCommand());
        return commandListener;
    }

    @Bean
    public RootCommand rootCommand() {
        if(rootCommand == null) {
            rootCommand = new RootCommand(botProperty.getCommandPrefix());

            AuthorizeCommand authorizeCommand = new AuthorizeCommand("인증", mailService, messageSenderService, authorizeService, messageViews, templateEngine);
            rootCommand.addChild(authorizeCommand);

            VoteCommand voteCommand = new VoteCommand("투표", voteScheduler);
            rootCommand.addChild(voteCommand);
        }
        return rootCommand;
    }
}