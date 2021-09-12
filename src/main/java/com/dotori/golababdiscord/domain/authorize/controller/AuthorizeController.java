package com.dotori.golababdiscord.domain.authorize.controller;

import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.enroll.service.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;
    private final EnrollService enrollService;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam String token) {
        ValidatedUserDto validatedUser = authorizeService.validateAuthorizeLink(token);
        enrollService.enroll(validatedUser.toUserDto());
        return "authorize/authorized";
    }
}
