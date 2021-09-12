package com.dotori.golababdiscord.domain.discord;

import com.dotori.golababdiscord.domain.discord.exception.BotBuildingFailureException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class SogoBot {
    private final JDA jda;

    public SogoBot(String botToken)  {
        try {
            jda = JDABuilder.createDefault(botToken).build();
        } catch (LoginException e) {
            throw new BotBuildingFailureException(e);
        }
    }
}
