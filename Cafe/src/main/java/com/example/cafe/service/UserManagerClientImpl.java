package com.example.cafe.service;

import com.example.cafe.api.model.UserData;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implements service providing requests to UserManager
 */

@Service
public class UserManagerClientImpl implements UserManagerClient {
    // Test value
    public static final String userManagerDomain = "http://localhost:8901";

    public UserData getUserData(String authHeaderValue) {
        try {
            var requestUrl = userManagerDomain + "/user/current";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, authHeaderValue);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            var restTemplate = new RestTemplate();
            ResponseEntity<UserData> response = restTemplate.exchange(
                    requestUrl, HttpMethod.GET, requestEntity, UserData.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            return null;
        } catch (Exception exception) {
            return null;
        }
    }
}
