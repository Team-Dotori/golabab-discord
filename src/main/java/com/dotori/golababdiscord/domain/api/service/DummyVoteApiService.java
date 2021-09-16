package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.RequestDayVoteResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Service("dummy-vote-api-service")
@Slf4j
public class DummyVoteApiService implements VoteApiService{
    @Override
    public void collectTotalVoteAtDay(RequestDayVoteResultDto result) {
        StringBuilder sb = new StringBuilder();
        addMenuAtLog(sb, result);
        log.info(sb.toString());
    }

    private static void addMenuAtLog(StringBuilder sb, RequestDayVoteResultDto result) {
        sb.append("조식 : \n");
        result.getBreakfast().getMenu().forEach(
                (k, v) -> sb.append(k).append(" | ").append(v).append("\n"));
        sb.append("중식 : \n");
        result.getLunch().getMenu().forEach(
                (k, v) -> sb.append(k).append(" | ").append(v).append("\n"));
        sb.append("석식 : \n");
        result.getDinner().getMenu().forEach(
                (k, v) -> sb.append(k).append(" | ").append(v).append("\n"));
    }
}
