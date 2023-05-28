package com.example.cafe.repository;

import com.example.cafe.model.CafeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Specifies User entity repository
 */
@Repository
public interface UserRepository extends JpaRepository<CafeUser, Long> {

    /**
     * Finds all User entities by Email
     * @param email email
     * @return list of found User entities
     */
    List<CafeUser> findByEmail(String email);
}
