package org.example.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.models.User;
import org.example.backend.models.Serie;
import org.example.backend.repository.SerieRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.JwtService;
import org.example.backend.service.LoginService;
import org.example.backend.service.UserService;
import org.example.backend.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    public final UserService service;
    public final RecommendationService serviceRecommandation;
    private final JwtService jwtService;
    private final LoginService loginService;
    private final SerieRepository serieRepository;
    private final UserRepository userRepository;


    public UserController(UserService service, RecommendationService serviceRecommandation, JwtService jwtService, LoginService loginService, SerieRepository serieRepository, UserRepository userRepository) {
        this.service = service;
        this.serviceRecommandation = serviceRecommandation;
        this.jwtService = jwtService;
        this.serieRepository = serieRepository;
        this.userRepository = userRepository;
        this.loginService = loginService;

    }

    ///////////////////// GET ///////////////////////////////////

    // Récupère tous les utilisateurs
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        jwtService.validateToken(authHeader);
        return ResponseEntity.ok(service.findAllUsers());
    }

    // Récupère l'historique d'un utilisateur connecté
    @GetMapping("/search")
    public User getUserByName(@RequestParam String name) {
        return service.findUsersByName(name);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistoryById(@RequestHeader("Authorization") String authheader){
        try {
            String token = jwtService.validateAndReturnToken(authheader);
            String email = loginService.extractEmail(token);
            List<Serie> history = service.findHistoryByEmail(email);

            return ResponseEntity.ok(history);
        } catch (RuntimeException error){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", error.getMessage()));
        }
    }

    // Récupère un utilisateur par id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    // Retourne des recommandations pour l'utilisateur connecté
    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendation(@RequestHeader("Authorization") String authheader){
        try {

            String token = jwtService.validateAndReturnToken(authheader);
            String email = loginService.extractEmail(token);

            return ResponseEntity.ok(serviceRecommandation.getRecommendation(email));
        } catch (RuntimeException error){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", error.getMessage()));
        }
    }

    ///////////////////// POST ///////////////////////////////////

    // Crée un nouvel utilisateur
    @PostMapping("/createUser")
    public User createUser(@RequestBody User newUser) {
        return service.createUser(newUser);
    }

    ///////////////////// PUT ///////////////////////////////////

    // Marque une série comme vue
    @PutMapping("/history/{seriesId}")
    public ResponseEntity<?> markSerieAsView(@RequestHeader("Authorization") String authHeader, @PathVariable Long seriesId) {
        try {
            String token = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(token);
            if (email == null){
                throw new RuntimeException("Email introuvable");
            }

            return ResponseEntity.ok(service.markSerieAsView(email, seriesId));
        } catch (RuntimeException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", error.getMessage()));
        }
    }

    // Met à jour les informations d'un utilisateur existant
    @PutMapping("/updateUser/{id}")
    public User updateUser(@RequestBody User newOne, @PathVariable Long id) {
        return service.updateUser(newOne, id);
    }

    ///////////////////// DELETE ///////////////////////////////////

    // Supprime un utilisateur
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}