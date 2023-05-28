package com.example.cafe.service;

import org.springframework.stereotype.Service;

/**
 * Implements testing service providing application secret managements
 */
@Service
public class SecretManagerImpl implements SecretManager {

    public String getJwtSecret() {
        return "JwtSecret";
    }
}
