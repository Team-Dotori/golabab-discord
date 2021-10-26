package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;

public interface ViewerApiService {
    RequestRankingDto getRanking(int start, int end);
}
