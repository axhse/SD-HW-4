package com.example.cafe.service;

import com.example.cafe.model.CafeUser;
import com.example.cafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implements service providing User recording
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public CafeUser findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public CafeUser findUserByEmail(String email) {
        var users = userRepository.findByEmail(email);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public Boolean trySaveUser(CafeUser user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
