package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.models.User;
import org.example.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Login", description = "Endpoints pour le login")
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(
            summary = "Login",
            description = "permet a l'utilisateur d'avoir le contenu de la page. " +
                    "L'utilisateur doit être authentifié pour accéder à cette ressource",
            responses = {
                @ApiResponse(responseCode = "200", description = "Connexion réussie"),
                @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect"),
                @ApiResponse(responseCode = "400", description = "Requête invalide ")
            }

    )
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
