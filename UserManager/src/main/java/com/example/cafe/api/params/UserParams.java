package com.example.cafe.api.params;

import lombok.Data;

/**
 * User request params
 */
@Data
public class UserParams {

    private String username;

    private String email;

    private String password;

    private String role;
}
