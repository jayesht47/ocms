package com.jayesh.ocms.services;

import com.jayesh.ocms.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000L;

    @Value("${JWT_SECRET}")
    private String jwtSecretKey;

    @Getter
    SecretKey key;

    @PostConstruct
    void init() {
        key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extarctAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extarctAllClaims(String token) throws SignatureException {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        Date tokenExpiry = extractClaim(token, Claims::getExpiration);
        return tokenExpiry.before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String userNameFromToken = getUserNameFromToken(token);
        return userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(user.getUsername(), claims);
    }

    public String createToken(String subject, Map<String, Object> claims) {
        return Jwts.
                builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .audience().add("ocms").and()
                .signWith(key)
                .compact();
    }

    public String createToken(String subject) {
        return Jwts.
                builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .audience().add("ocms").and()
                .signWith(key)
                .compact();
    }

}
