package org.example.backend.controller;

import org.example.backend.models.User;
import org.example.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String token = loginService.login(user.getEmail(), user.getPassword());
            //String email = loginService.extractEmail(token);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (RuntimeException e) {
            // En cas d'échec (email/mot de passe incorrect)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email ou mot de passe incorrect"));
        }
    }

}
