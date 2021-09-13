package com.dotori.golababdiscord.domain.discord;

import com.dotori.golababdiscord.domain.discord.exception.BotBuildingFailureException;
import com.dotori.golababdiscord.domain.discord.exception.UserNotFoundException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.EventListener;

public class SogoBot {
    private final JDA jda;

    public SogoBot(String token)  {
        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (Exception e) {
            throw new BotBuildingFailureException(e);
        }
    }

    public void addEventListener(EventListener listener) {
        jda.addEventListener(listener);
    }
    public MessageChannel getPrivateChannelByUserId(Long userId) {
        return getUserById(userId).openPrivateChannel().complete();
    }

    private User getUserById(Long userId) {
        User user;

        if((user = jda.retrieveUserById(userId).complete()) == null) throw new UserNotFoundException(userId);
        return user;
    }
}
