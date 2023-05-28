package com.example.cafe.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Specifies jwt helping service providing jwt token creating and parsing
 */
public interface JwtHelper {
    String TOKEN_HEADER_NAME = "Authorization";
    String TOKEN_HEADER_PREFIX = "Bearer ";

    /**
     * Builds jwt-token based on Session Token as a claim value
     * @param sessionToken session token
     * @return jwt-token
     */
    String buildJwtToken(String sessionToken);

    /**
     * Parses Session Token from Request value
     * @param request request to be parsed
     * @return session token
     */
    String getSessionToken(HttpServletRequest request);
}
