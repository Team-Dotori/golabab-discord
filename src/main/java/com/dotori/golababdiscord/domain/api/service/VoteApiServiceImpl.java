package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.RequestDayVoteResultDto;
import com.dotori.golababdiscord.domain.api.property.ApiProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
//@Service
public class VoteApiServiceImpl implements VoteApiService{
    private final ApiService<RequestDayVoteResultDto> apiService;
    private final ApiProperty apiProperty;

    @Override
    public void collectTotalVoteAtDay(RequestDayVoteResultDto result) {
        apiService.post(apiProperty.getBaseUrl() + ":" +  apiProperty.getPort() + "/api/v1/vote/collect-total-vote-at-day", result, RequestDayVoteResultDto.class);
    }
}
