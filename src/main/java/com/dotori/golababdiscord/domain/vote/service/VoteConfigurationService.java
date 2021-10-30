package com.dotori.golababdiscord.domain.vote.service;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface VoteConfigurationService {
    void changeChannel(MessageChannel channel);
    void checkChannel(User user);
}
