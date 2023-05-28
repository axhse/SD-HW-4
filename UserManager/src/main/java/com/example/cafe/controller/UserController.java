package com.example.cafe.controller;

import com.example.cafe.api.message.*;
import com.example.cafe.api.model.*;
import com.example.cafe.api.params.*;
import com.example.cafe.model.CafeUser;
import com.example.cafe.model.UserRole;
import com.example.cafe.service.JwtHelper;
import com.example.cafe.service.SessionService;
import com.example.cafe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements UserManager API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final JwtHelper jwtHelper;
    private final SessionService sessionService;
    private final UserService userService;

    /**
     * Creates a user
     * @param params user creation params
     */
    @PostMapping("/create")
    public ResponseEntity<Message> create(UserParams params) {
        if (params == null) {
            return ResponseEntity.badRequest().body(new Message("Params are missed"));
        }
        if (!CafeUser.isValidEmail(params.getEmail())) {
            return ResponseEntity.badRequest().body(new Message("Invalid email value"));
        }
        if (!CafeUser.isValidPassword(params.getPassword())) {
            return ResponseEntity.badRequest().body(new Message("Invalid password value"));
        }
        if (!CafeUser.isValidUsername(params.getUsername())) {
            return ResponseEntity.badRequest().body(new Message("Invalid username value"));
        }
        if (!UserRole.isValidRole(params.getRole())) {
            return ResponseEntity.badRequest().body(new Message("Invalid role value"));
        }
        var passwordHash = new BCryptPasswordEncoder().encode(params.getPassword());
        var user = new CafeUser( null, params.getEmail(), params.getUsername(), params.getRole(),
                                passwordHash, null, null);
        if (!userService.trySaveUser(user)) {
            return ResponseEntity.badRequest().body(new Message("Username or email is already used"));
        }
        return new ResponseEntity<>(new Message("Created"), HttpStatus.CREATED);
    }

    /**
     * Returns user information
     * @param userId user id\
     */
    @GetMapping("/get")
    public ResponseEntity<UserData> get(Long userId) {
        var user = userService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        var userData = new UserData(user.getId(), user.getEmail(), user.getUsername(), user.getRole());
        return ResponseEntity.ok(userData);
    }

    /**
     * Returns information of current authenticated user
     * @param auth current authentication token
     */
    @GetMapping("/current")
    public ResponseEntity<UserData> getCurrent(UsernamePasswordAuthenticationToken auth) {
        var principal = auth.getPrincipal();
        if (principal instanceof String) {
            var session = sessionService.findSessionByToken((String)principal);
            if (session != null) {
                var user = userService.findUserById(session.getUserId());
                if (user != null) {
                    var userData = new UserData(user.getId(), user.getEmail(), user.getUsername(), user.getRole());
                    return ResponseEntity.ok(userData);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Authenticates user
     * @param params login params
     */
    @PostMapping("/login")
    public ResponseEntity<Message> login(LoginParams params) {
        if (params == null) {
            return ResponseEntity.badRequest().body(new Message("Params are missed"));
        }
        if (!CafeUser.isValidEmail(params.getEmail())) {
            return ResponseEntity.badRequest().body(new Message("Invalid email value"));
        }
        if (!CafeUser.isValidPassword(params.getPassword())) {
            return ResponseEntity.badRequest().body(new Message("Invalid password value"));
        }
        var user = userService.findUserByEmail(params.getEmail());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        var encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(params.getPassword(), user.getPasswordHash())) {
            return new ResponseEntity<>(new Message("Password does not match"), HttpStatus.FORBIDDEN);
        }
        var session = sessionService.createSession(user.getId());
        if (session == null) {
            var message = new Message("Session can not be created. User is possibly deleted");
            return ResponseEntity.internalServerError().body(message);
        }
        var jwtToken = jwtHelper.buildJwtToken(session.getSessionToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, JwtHelper.TOKEN_HEADER_PREFIX + jwtToken)
                .body(new LoginMessage(jwtToken));
    }
}
