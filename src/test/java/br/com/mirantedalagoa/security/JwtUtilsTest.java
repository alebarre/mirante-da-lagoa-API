package br.com.mirantedalagoa.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilsTest {

    private final JwtUtils jwtUtils = new JwtUtils();

    @BeforeEach
    void setup() {
        String secret = Base64.getEncoder().encodeToString("uma-chave-super-segura-com-pelo-menos-512-bits-de-comprimento-para-hs512".getBytes());
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtUtils, "accessTokenExpirationMs", 900000);
        ReflectionTestUtils.setField(jwtUtils, "refreshTokenExpirationMs", 604800000);
    }

    @Test
    void deveGerarRefreshTokenValido() {
        UUID userId = UUID.randomUUID();
        String token = jwtUtils.generateRefreshToken(userId);

        assertThat(jwtUtils.validateRefreshToken(token)).isTrue();
        assertThat(jwtUtils.validateAccessToken(token)).isFalse();
        assertThat(UUID.fromString(jwtUtils.getUserIdFromToken(token))).isEqualTo(userId);
    }

    @Test
    void deveExtrairClaimsDoToken() {
        UUID userId = UUID.randomUUID();
        String token = jwtUtils.generateRefreshToken(userId);

        Claims claims = jwtUtils.getClaims(token);
        assertThat(claims.getSubject()).isEqualTo(userId.toString());
        assertThat(claims.get("type")).isEqualTo("refresh");
    }
}
