package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Utilisateurs", description = "Endpoints pour la gestion des utilisateurs")
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

    ///////////////////////////////////
    // GET - Tous les utilisateurs
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir la liste de tous les utilisateurs",
            description = "Retourne tous les utilisateurs existants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        jwtService.validateToken(authHeader);
        return ResponseEntity.ok(service.findAllUsers());
    }

    ///////////////////////////////////
    // GET - Rechercher un utilisateur par nom
    ///////////////////////////////////
    @Operation(
            summary = "Rechercher un utilisateur par nom",
            description = "Permet de retrouver un utilisateur par son nom.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur trouvée"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            }
    )
    @GetMapping("/search")
    public User getUserByName(@RequestParam String name) {
        return service.findUsersByName(name);
    }

    ///////////////////////////////////
    // GET - Historique des series
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir l'historique de visionnage d'un utilisateur",
            description = "Retourne la liste des séries que l'utilisateur a déjà regardées. ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Historique récupéré avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
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
    ///////////////////////////////////
    // GET - Utilisateur par ID
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir un utilisateur par ID",
            description = "Retourne les informations d'un utilisateur spécifique",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur trouvée"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            }
    )
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    ///////////////////////////////////
    // PUT - Marquer une serie comme vue
    ///////////////////////////////////
    @Operation(
            summary = "Marquer une série comme vue par l'utilisateur",
            description = "Permet d'ajouter une série à l'historique de l'utilisateur connecté.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Série marquée comme vue avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Série non trouvée")
            }
    )
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

    ///////////////////////////////////
    // GET - Recommandations
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir des recommandations de series",
            description = "Retourne une liste de series recommandées pour l'utilisateur ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recommandations récupérées avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
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

    ///////////////////////////////////
    // POST - Créer un utilisateur
    ///////////////////////////////////
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = "Ajoute un nouvel utilisateur.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    @PostMapping("/createUser")
    public User createUser(@RequestBody User newUser) {
        return service.createUser(newUser);
    }

    ///////////////////////////////////
    // PUT - Mettre à jour un utilisateur
    ///////////////////////////////////
    @Operation(
            summary = "Mettre à jour un utilisateur existant",
            description = "Modifie les informations d'un utilisateur specifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    @PutMapping("/updateUser/{id}")
    public User updateUser(@RequestBody User newOne, @PathVariable Long id) {
        return service.updateUser(newOne, id);
    }

    ///////////////////// DELETE ///////////////////////////////////

    ///////////////////////////////////
    // DELETE - Supprimer un utilisateur
    ///////////////////////////////////
    @Operation(
            summary = "Supprimer un utilisateur",
            description = "Supprime un utilisateur existant.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            }
    )
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}