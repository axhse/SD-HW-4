package com.example.cafe.service;

import com.example.cafe.model.CafeUser;

/**
 * Specifies service providing User entities recording
 */
public interface UserService {

    /**
     * Finds User entity by User Id
     * @param id user id
     * @return found User entity
     */
    CafeUser findUserById(Long id);

    /**
     * Finds User entity by Email
     * @param email email
     * @return found User entity
     */
    CafeUser findUserByEmail(String email);

    /**
     * Saves User entity
     * @param user User entity to save
     * @return true if entity was saved, otherwise false
     */
    Boolean trySaveUser(CafeUser user);
}
