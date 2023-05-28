package com.example.cafe.service;

/**
 * Specifies service providing application secret managements
 */
public interface SecretManager {

    /**
     * Returns jwt hmac secret key
     * @return jwt hmac secret key
     */
    String getJwtSecret();
}
