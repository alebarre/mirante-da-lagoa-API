package br.com.mirantedalagoa.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.access-token-expiration-ms}")
    private int accessTokenExpirationMs;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private int refreshTokenExpirationMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
            .subject(userPrincipal.getId().toString())
            .claim("email", userPrincipal.getEmail())
            .claim("fullName", userPrincipal.getFullName())
            .claim("type", "access")
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + accessTokenExpirationMs))
            .signWith(key(), Jwts.SIG.HS512)
            .compact();
    }

    public String generateAccessTokenFromEmail(String email, String fullName, UUID id) {
        return Jwts.builder()
            .subject(id.toString())
            .claim("email", email)
            .claim("fullName", fullName)
            .claim("type", "access")
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + accessTokenExpirationMs))
            .signWith(key(), Jwts.SIG.HS512)
            .compact();
    }

    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
            .subject(userId.toString())
            .claim("type", "refresh")
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + refreshTokenExpirationMs))
            .signWith(key(), Jwts.SIG.HS512)
            .compact();
    }

    public String getUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        return parseClaims(token);
    }

    public boolean validateAccessToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return "access".equals(claims.get("type"));
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return "refresh".equals(claims.get("type"));
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(key())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
