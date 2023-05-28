package com.example.cafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;

/**
 * User domain model
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CafeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String role;

    private String passwordHash;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Checks whether string is a valid Password value
     * @param password string value to be checked
     */
    public static Boolean isValidPassword(String password) {
        return password != null && !password.isEmpty() && password.length() <= 100;
    }

    /**
     * Checks whether string is a valid Username value
     * @param username string value to be checked
     */
    public static Boolean isValidUsername(String username) {
        if (username == null || username.isEmpty() || username.length() > 50) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_.]+$");
    }

    /**
     * Checks whether string is a valid Email value
     * @param email string value to be checked
     */
    public static Boolean isValidEmail(String email) {
        if (email == null || email.isEmpty() || email.length() > 100) {
            return false;
        }
        return EmailValidator.getInstance().isValid(email);
    }
}
