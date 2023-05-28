package com.example.cafe.service;

import com.example.cafe.model.CafeSession;

/**
 * Specifies service providing Session entities recording
 */
public interface SessionService {

    /**
     * Creates Session entity for User ID
     * @param userId user id
     * @return created Session entity
     */
    CafeSession createSession(Long userId);

    /**
     * Finds Session entity by Session Token
     * @param sessionToken session token
     * @return found Session entity
     */
    CafeSession findSessionByToken(String sessionToken);
}
