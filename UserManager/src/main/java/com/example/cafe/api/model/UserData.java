package com.example.cafe.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User response body data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private Long id;

    private String email;

    private String username;

    private String role;
}
