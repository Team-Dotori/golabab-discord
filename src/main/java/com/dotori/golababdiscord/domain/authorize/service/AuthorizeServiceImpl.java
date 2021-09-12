package com.dotori.golababdiscord.domain.authorize.service;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.authorize.util.AuthTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {
    private final AuthTokenUtils authTokenUtils;
    @Value("${server.address}")
    String host;
    @Value("${server.port}")
    String port;

    @Override
    public ValidatedUserDto validateAuthorizeLink(String token) {
        authTokenUtils.validateToken(token);
        return authTokenUtils.decodeToken(token);
    }

    @Override
    public String generateAuthorizeLink(DomainValidatedUserDto user) {
        String token = authTokenUtils.generateToken(user);
        return generateAuthorizeLinkByToken(token);
    }
    private String generateAuthorizeLinkByToken(String token) {
        return host + ":" + port + "/authorize?token" + token;
    }

    @Override
    public DomainValidatedUserDto validateDomain(UnValidatedUserDto user, String email) {
        String domain = getDomainFromEmail(email);
        DepartmentType department = getDepartmentFromDomain(domain);
        return user.toDomainValidateUserDto(email, department);
    }
    private String getDomainFromEmail(String email) {
        return email.substring(email.indexOf("@") + 1);
    }
    private DepartmentType getDepartmentFromDomain(String domain) {
        for (DepartmentType value : DepartmentType.values()) {
            if(value.getDomain().equals(domain))  return value;
        } throw new DepartmentNotFoundException(domain);
    }
}
