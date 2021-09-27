package com.dotori.golababdiscord.domain.vote.service;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public interface VoteConfigurationService {
    void changeChannel(MessageChannel channel);
    void checkChannel(User user);
}
