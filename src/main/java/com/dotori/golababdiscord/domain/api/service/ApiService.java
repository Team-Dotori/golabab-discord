package com.dotori.golababdiscord.domain.api.service;

import lombok.RequiredArgsConstructor;
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

    public void post(String url, T body, Class<T> clazz) {
        restTemplate.postForObject(url, body, clazz);
    }

}
