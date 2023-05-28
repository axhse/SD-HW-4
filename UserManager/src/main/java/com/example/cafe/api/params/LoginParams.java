package com.example.cafe.api.params;

import lombok.Data;

/**
 * Login request params
 */
@Data
public class LoginParams {

    private String email;

    private String password;
}
