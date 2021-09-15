package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.RequestDayVoteResultDto;
import org.springframework.stereotype.Service;

@Service
public interface VoteApiService {
    void collectTotalVoteAtDay(RequestDayVoteResultDto result);
}
