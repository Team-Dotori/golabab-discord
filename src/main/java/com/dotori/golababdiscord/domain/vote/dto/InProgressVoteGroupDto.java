package com.dotori.golababdiscord.domain.vote.dto;

import com.dotori.golababdiscord.domain.vote.exception.EmptyVoteGroupException;
import lombok.Getter;

import java.util.*;

@Getter
public class InProgressVoteGroupDto {
    private final Date date;
    private final List<InProgressVoteDto> votes;

    public InProgressVoteGroupDto(InProgressVoteDto... votes) {
        this(List.of(votes));
    }

    public InProgressVoteGroupDto(List<InProgressVoteDto> votes) {
        this.votes = votes;
        this.date = new Date();
        setDate(date);
    }

    private void setDate(Date date) {
        votes.stream().filter(Objects::nonNull).findAny().ifPresentOrElse(
                vote -> date.setTime(vote.getVoteDate().getTime()),
                () -> {throw new EmptyVoteGroupException(votes);}
        );
    }
}
