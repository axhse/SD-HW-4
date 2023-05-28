package com.example.cafe.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements jwt helping service
 */
@Service
@RequiredArgsConstructor
public class JwtHelperImpl implements JwtHelper {
    public static final String CLAIM_HEY = "sessionToken";

    @Autowired
    private final SecretManager secretManager;

    public String buildJwtToken(String sessionToken) {
        return Jwts.builder()
                        .claim(CLAIM_HEY, sessionToken)
                        .signWith(SignatureAlgorithm.HS256, secretManager.getJwtSecret())
                        .compact();
    }

    public String getSessionToken(HttpServletRequest request) {
        String authHeader = request.getHeader(TOKEN_HEADER_NAME);
        if (authHeader == null || !authHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            return null;
        }
        String jwtToken = authHeader.replace(TOKEN_HEADER_PREFIX, "");

        try {
            var claims = Jwts.parser()
                    .setSigningKey(secretManager.getJwtSecret())
                    .parseClaimsJws(jwtToken)
                    .getBody();
            return claims.get(CLAIM_HEY, String.class);
        } catch (Exception exception) {
            return null;
        }
    }
}
