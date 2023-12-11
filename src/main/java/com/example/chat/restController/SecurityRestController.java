package com.example.chat.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Handles security for the REST API.
 *
 * Use JWT for authentication and authorization of incoming requests.
 */
@RestController
@RequestMapping("api/v1")
public class SecurityRestController {

    @Autowired
    JwtEncoder encoder;

    //@PostMapping({"login", "signin"})
    @PostMapping("login")
    public String login(Authentication authentication) {
        Instant issuedAt = Instant.now();
        // 24h in milliseconds
        long expirationTime = 86400000L;

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusSeconds(expirationTime))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }


}
