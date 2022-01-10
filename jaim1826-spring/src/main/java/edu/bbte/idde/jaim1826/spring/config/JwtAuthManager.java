package edu.bbte.idde.jaim1826.spring.config;

import edu.bbte.idde.jaim1826.spring.model.User;
import edu.bbte.idde.jaim1826.spring.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthManager implements AuthenticationManager {
    @Autowired
    TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        boolean valid = tokenManager.validateToken(token);
        if (valid) {
            return new UsernamePasswordAuthenticationToken(token, token);
        }
        throw new BadCredentialsException("Invalid token!");
    }
}
