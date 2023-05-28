package com.example.cafe.repository;

import com.example.cafe.model.CafeSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Specifies Session entity repository
 */
@Repository
public interface SessionRepository extends JpaRepository<CafeSession, Long> {

    /**
     * Finds all Session entities by Session Token
     * @param sessionToken session token
     * @return list of found Session entities
     */
    List<CafeSession> findBySessionToken(String sessionToken);
}
