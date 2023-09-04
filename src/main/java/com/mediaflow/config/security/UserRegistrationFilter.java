package com.mediaflow.config.security;

import com.mediaflow.entity.User;
import com.mediaflow.repository.UserRepository;
import com.mediaflow.util.LockByKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.security.Principal;

@Component
@AllArgsConstructor
public class UserRegistrationFilter extends GenericFilterBean {

    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Principal principal = ((SecurityContextHolderAwareRequestWrapper) request).getUserPrincipal();
        Jwt token = ((JwtAuthenticationToken) principal).getToken();

        String userId = token.getSubject();
        LockByKey lockByKey = new LockByKey();

        try {
            lockByKey.lock(userId);
            if (!userRepository.existsById(userId)) {
                User user = User.builder()
                        .id(userId)
                        .email(token.getClaimAsString("email"))
                        .username(token.getClaimAsString("preferred_username"))
                        .firstName(token.getClaimAsString("given_name"))
                        .lastName(token.getClaimAsString("family_name"))
                        .build();

                userRepository.save(user);
            }
        } finally {
            lockByKey.unlock(userId);
        }

        chain.doFilter(request, response);
    }

}
