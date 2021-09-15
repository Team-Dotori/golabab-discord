package com.dotori.golababdiscord.domain.vote.repository;

import com.dotori.golababdiscord.domain.vote.entity.InProgressVote;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface InProgressVoteRepository extends JpaRepository<InProgressVote, Long> {
    default void save() {

    }
    InProgressVote getByVoteDateAndMeal(Date voteDate, MealType meal);
}
