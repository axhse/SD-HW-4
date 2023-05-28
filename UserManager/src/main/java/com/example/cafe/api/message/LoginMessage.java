package com.example.cafe.api.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Login response body message
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginMessage extends Message {

    public LoginMessage(String jwtToken) {
        super("Logged in. Specify jwt token via authorization header");
        bearerToken = jwtToken;
    }

    private String bearerToken;
}
