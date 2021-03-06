package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.RequestCollectedVoteDto;
import com.dotori.golababdiscord.domain.api.property.VoteApiProperty;
import com.dotori.golababdiscord.domain.logger.annotation.ApiEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VoteApiServiceImpl implements VoteApiService{
    private final ApiService<String> apiService;
    private final VoteApiProperty voteApiProperty;

    @Override
    public void collectTotalVoteAtDay(RequestCollectedVoteDto result) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, voteApiProperty.getClientToken());
        apiService. post(voteApiProperty.getBaseUrl() + ":" +  voteApiProperty.getPort() + "/api/v1/vote/collect-vote-at-day",
                result, String.class, headers);
    }
}
