package com.dotori.golababdiscord.domain.vote.enum_type;

import com.dotori.golababdiscord.domain.vote.exception.VoteEmojiNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.util.Arrays;
import java.util.Optional;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public enum VoteEmoji {
    ONE(0, "\u0031\u20E3"),
    TWO(1, "\u0032\u20E3"),
    THREE(2, "\u0033\u20E3"),
    FOUR(3, "\u0034\u20E3"),
    FIVE(4, "\u0035\u20E3"),
    SIX(5, "\u0036\u20E3"),
    SEVEN(6, "\u0037\u20E3"),
    EIGHT(7, "\u0038\u20E3"),
    NINE(8, "\u0039\u20E3");

    private final Integer id;
    private final String emoji;

    public static VoteEmoji of(Integer id) {
        Optional<VoteEmoji> result = Arrays.stream(values()).filter(voteEmoji -> voteEmoji.getId().equals(id)).findAny();
        if(result.isEmpty()) throw new VoteEmojiNotFoundException(id);
        return result.get();
    }

    public static boolean isVoteEmoji(MessageReaction reaction) {
        String emojiName = reaction.getReactionEmote().getName();
        return Arrays.stream(VoteEmoji.values())
                .anyMatch(emoji -> emojiName.equals(emoji.getEmoji()));
    }
}
