package org.example.backend;

import org.example.backend.service.JwtService;
import org.example.backend.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private JwtService jwtService;

    private final String validToken = "valid.token.here";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateToken_InvalidHeader() {
        ResponseEntity<String> response = jwtService.validateToken("InvalidHeader");
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Token manquant", response.getBody());
    }

    @Test
    void testValidateToken_InvalidToken() {
        when(loginService.isTokenValid(validToken)).thenReturn(false);

        ResponseEntity<String> response = jwtService.validateToken("Bearer " + validToken);
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Token invalide ou expiré", response.getBody());
    }

    @Test
    void testValidateToken_ValidToken() {
        when(loginService.isTokenValid(validToken)).thenReturn(true);

        ResponseEntity<String> response = jwtService.validateToken("Bearer " + validToken);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Token valide", response.getBody());
    }

    @Test
    void testValidateAndReturnToken_InvalidHeader() {
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                jwtService.validateAndReturnToken("InvalidHeader"));
        assertEquals("Token manquant", ex.getMessage());
    }

    @Test
    void testValidateAndReturnToken_InvalidToken() {
        when(loginService.isTokenValid(validToken)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                jwtService.validateAndReturnToken("Bearer " + validToken));
        assertEquals("Token invalide ou expiré", ex.getMessage());
    }

    @Test
    void testValidateAndReturnToken_ValidToken() {
        when(loginService.isTokenValid(validToken)).thenReturn(true);

        String token = jwtService.validateAndReturnToken("Bearer " + validToken);
        assertEquals(validToken, token);
    }
}
