package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.RequestCollectedVoteDto;
import lombok.extern.slf4j.Slf4j;

//@Service("dummy-vote-api-service")
@Slf4j
public class DummyVoteApiService implements VoteApiService{
    @Override
    public void collectTotalVoteAtDay(RequestCollectedVoteDto result) {
        StringBuilder sb = new StringBuilder();
        addMenuAtLog(sb, result);
        log.info(sb.toString());
    }

    private static void addMenuAtLog(StringBuilder sb, RequestCollectedVoteDto result) {
        if (result.getBreakfast() != null) {
            sb.append("조식 : \n");
            result.getBreakfast().getMenus().forEach(
                    (k, v) -> sb.append(k).append(" | ").append(v).append("\n"));
        }
        if (result.getLunch() != null) {
            sb.append("중식 : \n");
            result.getLunch().getMenus().forEach(
                    (k, v) -> sb.append(k).append(" | ").append(v).append("\n"));
        }
        if (result.getDinner() != null) {
            sb.append("석식 : \n");
            result.getDinner().getMenus().forEach(
                    (k, v) -> sb.append(k).append(" | ").append(v).append("\n"));
        }
    }
}
