package com.raven.birdmail.Utils;

import com.raven.birdmail.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final long EXPIRATION = 3600000;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getSubject();
        } catch (JwtException e) {
            System.out.println("Error parsing token: " + e.getMessage());
            return null;
        }
    }
}
