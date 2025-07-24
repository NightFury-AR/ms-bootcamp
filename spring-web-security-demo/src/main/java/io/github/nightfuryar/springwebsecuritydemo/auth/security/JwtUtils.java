package io.github.nightfuryar.springwebsecuritydemo.auth.security;


import io.github.nightfuryar.springwebsecuritydemo.auth.service.CustomUserDetailsManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.issuer:NightFury}")
    private String jwtIssuer;

    @Value("${jwt.expiration:1000}")
    private long jwtExpiration;

    @Value("${jwt.secret:nightfury}")
    private String jwtSecret;

    @Autowired private CustomUserDetailsManager customUserDetailsManager;

    public String generateToken(UserDetails user) {
        try {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList())
                    .setIssuer(jwtIssuer)
                    .setIssuedAt(new java.util.Date())
                    .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                    .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), io.jsonwebtoken.SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            log.error("Error generating JWT token: {}", e.getMessage());
            throw new RuntimeException("Error generating JWT token", e);
        }

    }


    public String extractUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new java.util.Date());
    }

    public boolean validateToken(String token,UserDetails user) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public UserDetails loadUserByUsername(String username) {
        return customUserDetailsManager.loadUserByUsername(username);
    }
}
