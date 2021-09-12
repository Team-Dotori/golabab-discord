package com.dotori.golababdiscord.domain.authorize.service;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizeService {
    ValidatedUserDto validateAuthorizeLink(String token);
    String generateAuthorizeLink(DomainValidatedUserDto user);
    DomainValidatedUserDto validateDomain(UnValidatedUserDto user, String email);
}
