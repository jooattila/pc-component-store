package edu.bbte.idde.jaim1826.spring.service;

import edu.bbte.idde.jaim1826.spring.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {
    private JwtParser parser = Jwts.parser();
    private JwtBuilder jwtBuilder = Jwts.builder();
    private static String jwtKey = "r4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E";

    public String createToken(User user) {
        return jwtBuilder.setSubject(user.getUsername())
                .claim("isAdmin", user.getIsAdmin())
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parser.setSigningKey(jwtKey).parse(token);
            return true;
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
