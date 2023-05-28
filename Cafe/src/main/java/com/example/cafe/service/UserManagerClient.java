package com.example.cafe.service;


import com.example.cafe.api.model.UserData;

/**
 * Specifies service providing requests to UserManager
 */
public interface UserManagerClient {

    /**
     * Receives User data by jwt-token
     * @param authHeaderValue authentication header value
     * @return data of authenticated User
     */
    UserData getUserData(String authHeaderValue);
}
