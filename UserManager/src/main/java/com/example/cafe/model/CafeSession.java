package com.example.cafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Session domain model
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CafeSession {
    public static final Integer sessionLifetimeInSeconds = 3600;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String sessionToken;

    private LocalDateTime expiresAt;

    /**
     * Calculates expiration time based on current time and session lifetime
     * @return expiration date
     */
    public static LocalDateTime calculateExpirationTime() {
        return LocalDateTime.now().plusSeconds(sessionLifetimeInSeconds);
    }

    /**
     * Checks whether string is a valid Session Token value
     * @param token string value to be checked
     */
    public static Boolean isValidToken(String token) {
        if (token == null || token.isEmpty() || token.length() > 255) {
            return false;
        }
        return token.matches("^[a-zA-Z0-9-]+$");
    }

    /**
     * Checks whether Session is expired
     */
    public Boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isBefore(expiresAt);
    }
}
