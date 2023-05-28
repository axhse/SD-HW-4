package com.example.cafe.auth;

import com.example.cafe.service.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Implements jwt middleware provider
 * Provides authentication based on jwt-tokens
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    /**
     * Handles current request
     * Authenticates user if jwt-token is presented
     * @param request processing request
     * @param response processing request
     * @param chain middleware chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        var sessionToken = jwtHelper.getSessionToken(request);
        if (sessionToken != null) {
            var auth = new UsernamePasswordAuthenticationToken(sessionToken, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
