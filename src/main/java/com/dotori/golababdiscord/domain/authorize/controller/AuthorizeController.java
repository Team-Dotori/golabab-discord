package com.dotori.golababdiscord.domain.authorize.controller;

import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final AuthorizeService service;

    @RequestMapping("/authorize")
    public void authorize(@RequestParam String token) {
        service.validateAuthorizeLink(token);
    }
}
