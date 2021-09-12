package com.dotori.golababdiscord.domain.authorize.util;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.global.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthTokenUtils implements JwtUtils<DomainValidatedUserDto, ValidatedUserDto> {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public String generateToken(DomainValidatedUserDto user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(getExpiration(now))
                .addClaims(getClaimsByUser(user))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    private Map<String, Object> getClaimsByUser(DomainValidatedUserDto user) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", user.getDiscordId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("department", user.getDepartment().name());

        return map;
    }
    private Date getExpiration(Date issuedAt) {
        return new Date(issuedAt.getTime() + Duration.ofMinutes(10).toMillis());
    }

    @Override
    public void validateToken(String token) {
        if(token == null) throw new IllegalArgumentException("token is null");
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    @Override
    public ValidatedUserDto decodeToken(String token) {
        Claims claims = getClaimsByToken(token);
        return getUserByClaims(claims);
    }
    private Claims getClaimsByToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    private ValidatedUserDto getUserByClaims(Claims data) {
        return new ValidatedUserDto(
                data.get("id", Long.class),
                data.get("name", String.class),
                data.get("email", String.class),
                DepartmentType.of(data.get("department", String.class))
        );
    }
}
