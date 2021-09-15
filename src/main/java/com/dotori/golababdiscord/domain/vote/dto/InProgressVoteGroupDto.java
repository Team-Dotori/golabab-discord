package com.dotori.golababdiscord.domain.vote.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Getter
public class InProgressVoteGroupDto {
    private final Date date;
    private final InProgressVoteMemberDto breakfast;
    private final InProgressVoteMemberDto lunch;
    private final InProgressVoteMemberDto dinner;

    public InProgressVoteGroupDto(InProgressVoteDto breakfast,
                                  InProgressVoteDto lunch,
                                  InProgressVoteDto dinner) {
        checkDates(breakfast, lunch, dinner);
        this.date = breakfast.getVoteDate();
        this.breakfast = new InProgressVoteMemberDto(breakfast);
        this.lunch = new InProgressVoteMemberDto(lunch);
        this.dinner = new InProgressVoteMemberDto(dinner);
    }

    private void checkDates(InProgressVoteDto breakfast, InProgressVoteDto lunch, InProgressVoteDto dinner) {
        Calendar breakfastDate = Calendar.getInstance();
        breakfastDate.setTime(breakfast.getVoteDate());
        breakfastDate.get(Calendar.DATE);

        Calendar lunchDate = Calendar.getInstance();
        breakfastDate.setTime(lunch.getVoteDate());
        breakfastDate.get(Calendar.DATE);

        Calendar dinnerDate = Calendar.getInstance();
        breakfastDate.setTime(dinner.getVoteDate());
        breakfastDate.get(Calendar.DATE);
    }
}
