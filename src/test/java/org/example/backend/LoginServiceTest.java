package org.example.backend;

import org.example.backend.models.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    private User user;
    private final String rawPassword = "password123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        // Hash du mot de passe
        user.setPassword(new BCryptPasswordEncoder().encode(rawPassword));
    }

    @Test
    void testLoginSuccess() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);

        String token = loginService.login("test@example.com", rawPassword);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                loginService.login("test@example.com", rawPassword));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testLoginWrongPassword() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                loginService.login("test@example.com", "wrongpassword"));
        assertEquals("Your email or password is incorrect", ex.getMessage());
    }

    @Test
    void testIsTokenValid() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        String token = loginService.login("test@example.com", rawPassword);

        assertTrue(loginService.isTokenValid(token));
        assertFalse(loginService.isTokenValid(token + "invalid"));
    }

    @Test
    void testExtractEmail() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
        String token = loginService.login("test@example.com", rawPassword);

        String email = loginService.extractEmail(token);
        assertEquals("test@example.com", email);
    }
}
