package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.exception.LoadingRankingDataFailureException;
import com.dotori.golababdiscord.domain.api.property.VoteApiProperty;
import com.dotori.golababdiscord.domain.ranking.dto.RankingDto;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ViewerApiServiceImpl implements ViewerApiService {
    private final ApiService<String> apiService;
    private final VoteApiProperty voteApiProperty;

    @Override
    public RequestRankingDto getRanking(int start, int end) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, voteApiProperty.getClientToken());
        String responseStr =  apiService.get(voteApiProperty.getBaseUrl() + ":" +  voteApiProperty.getPort() +
                        String.format("/api/v1/views/menu-ranking?start=%d&end=%d&range=total", start, end),
                String.class, headers).getBody();

        return mapToDto(responseStr);
    }

    private RequestRankingDto mapToDto(String responseStr) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<HashMap<String, Object>>> map;
        List<RankingDto> list = new ArrayList<>();
        try {
            map = mapper.readValue(responseStr, Map.class);
        } catch (JsonProcessingException e) {
            throw new LoadingRankingDataFailureException(e);
        }
        map.get("list").forEach(ranking ->
                list.add(new RankingDto((String)ranking.get("menuName"), (int)ranking.get("numOfVote")))
        );

        return new RequestRankingDto(list);
    }
}
