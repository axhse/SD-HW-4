package com.example.cafe.service;

import com.example.cafe.model.CafeSession;
import com.example.cafe.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implements service providing Session recording
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    public CafeSession createSession(Long userId) {
        var sessionToken = UUID.randomUUID().toString();
        var expirationTime = CafeSession.calculateExpirationTime();
        var session = new CafeSession(null, userId, sessionToken, expirationTime);
        try {
            sessionRepository.save(session);
        } catch (Exception exception) {
            return null;
        }
        return session;
    }

    public CafeSession findSessionByToken(String sessionToken) {
        var sessions = sessionRepository.findBySessionToken(sessionToken);
        if (sessions.isEmpty()) {
            return null;
        }
        return sessions.get(0);
    }
}
