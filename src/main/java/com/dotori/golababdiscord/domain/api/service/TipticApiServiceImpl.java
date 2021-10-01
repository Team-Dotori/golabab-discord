package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.exception.ApiException;
import com.dotori.golababdiscord.domain.api.property.VoteApiProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipticApiServiceImpl implements TipticApiService{
    private final ApiService<String> apiService;
    private final VoteApiProperty voteApiProperty;

    @Override
    public String getImproveMessage() {
        ResponseEntity<String> response = apiService.get(voteApiProperty.getBaseUrl() + ":" +  voteApiProperty.getPort() + "/api/v1/tiptic/get-improve-message", String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            String message = response.getBody();
            return message.substring(2, message.length()-2);
        }
        else throw new ApiException(response.getStatusCode());
    }
}
