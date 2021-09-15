package com.dotori.golababdiscord.domain.vote.entity;

import com.dotori.golababdiscord.domain.vote.dto.InProgressVoteDto;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class InProgressVote {
    @Id
    private Long voteMessageId;
    private Date voteDate;
    @Enumerated(value = EnumType.STRING)
    private MealType meal;
}
