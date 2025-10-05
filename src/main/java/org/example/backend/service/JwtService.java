package org.example.backend.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final LoginService loginService;

    public JwtService(LoginService loginService) {
        this.loginService = loginService;
    }

    public ResponseEntity<String> validateToken(String authHeader) {
        // vérifie si le token est présent
        if (authHeader == null | !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token manquant");
        }

        // Extrait le token
        String token = authHeader.substring(7);

        // vérification de la validité du token
        if (!loginService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Token invalide ou expiré");
        }

        // si tout est bon
        return ResponseEntity.ok("Token valide");
    }


    public String validateAndReturnToken(String authHeader) {
        // vérifie si le token est présent
        if (authHeader == null | !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant");
        }

        // Extrait le token
        String token = authHeader.substring(7);

        // vérification de la validité du token
        if (!loginService.isTokenValid(token)) {
            throw new RuntimeException("Token invalide ou expiré");
        }

        // si tout est bon
        return token;
    }

}
