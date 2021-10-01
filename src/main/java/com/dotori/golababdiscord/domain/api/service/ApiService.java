package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.RequestCollectedVoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiService<T> {
    private final RestTemplate restTemplate;

    protected ResponseEntity<T> get(String url, Class<T> clazz) {
        return restTemplate.getForEntity(url, clazz);
    }

    public <T1> void post(String url, T1 body, Class<T> clazz) {
        HttpEntity<T1> request = new HttpEntity<>(body);
        restTemplate.postForEntity(url, request, clazz);
    }

    protected ResponseEntity<T> get(String url, Class<T> clazz, HttpHeaders headers) {
        HttpEntity request = new HttpEntity(headers);

        return restTemplate.exchange(url, HttpMethod.GET, request, clazz);
    }

    public <T1> void post(String url, T1 body, Class<T> clazz, HttpHeaders headers) {
        HttpEntity<T1> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(url, request, clazz);
    }
}