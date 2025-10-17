package org.example.backend.controller;

import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.models.Ratings;
import org.example.backend.repository.RatingsRepository;
import org.example.backend.service.JwtService;
import org.example.backend.service.LoginService;
import org.example.backend.service.RatingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.RuntimeOperationsException;
import java.util.List;
import java.util.Map;

@Tag(name = "Rating", description = "Endpoints pour la gestion de ratings")
@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final JwtService jwtService;
    private final RatingsService ratingsService;
    private final RatingsRepository ratingsRepository;
    private final LoginService loginService;

    // Injection par constructeur
    public RatingController(RatingsService ratingsService, JwtService jwtService, LoginService loginService, RatingsRepository ratingsRepository){
        this.ratingsService = ratingsService;
        this.jwtService = jwtService;
        this.loginService = loginService;
        this.ratingsRepository = ratingsRepository;
    }


    ///////////////////////////////////
    // GET - Tous les ratings
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir tous les ratings existants.",
            description = "Permet à l'utilisateur d'obtenir le contenu complet des ratings " +
                    "L'utilisateur doit être authentifié pour accéder à cette ressource.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des ratings recuperée avec sucess"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/getAllRates")
    public List<Ratings> getAllRates(){
        return ratingsRepository.findAll();
    }


    ///////////////////////////////////
    // GET - Moyenne des series
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir la moyenne des ratings d'une série",
            description = "Retourne la moyenne des notes pour une série.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moyenne de la série récupérée avec succes"),
                    @ApiResponse(responseCode = "404", description = "Série non trouvée")
            }
    )
    @GetMapping("/serie/{id}")
    public ResponseEntity<?> getSerieAverage(@PathVariable Long id) {
        return ResponseEntity.ok(ratingsService.getAverageSerieRating(id));
    }


    ///////////////////////////////////
    // GET - Moyenne des épisodes
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir la moyenne des ratings d'une épisode",
            description = "Retourne la moyenne des notes pour une épisode.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moyenne de l'épisode récupérée avec succes"),
                    @ApiResponse(responseCode = "404", description = "Série non trouvée")
            }
    )
    @GetMapping("/episode/{id}")
    public ResponseEntity<?> getEpisodeAverage(@PathVariable Long id) {
        return ResponseEntity.ok(ratingsService.getAverageEpisodeRating(id));
    }

    ///////////////////////////////////
    // GET - Noter une épisode
    ///////////////////////////////////
    @Operation(
            summary = "Ajouter un rating à un épisode",
            description = "Permet à un utilisateur de donner une note a un épisode spécifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating ajoutée avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @PostMapping("/episode/{idEpisode}/{score}")
    public ResponseEntity<?> rateEpisode(@RequestHeader("Authorization") String authHeader, @PathVariable Long idEpisode, @PathVariable int score){
        try {
            String responseToken = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(responseToken);

            return ResponseEntity.ok(ratingsService.rateEpisode(idEpisode, email, score));

        } catch (RuntimeException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", error.getMessage()));
        }
    }

    ///////////////////////////////////
    // GET - Noter une serie
    ///////////////////////////////////
    @Operation(
            summary = "Ajouter un rating à une série",
            description = "Permet à un utilisateur de donner une note à une série specifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating ajoutée avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @PostMapping("/serie/{id}/{scoreRate}")
    public ResponseEntity<?> rateSerie(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @PathVariable int scoreRate){

        try {
            String responseToken = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(responseToken);


            return ResponseEntity.ok(ratingsService.rateSerie(id, email,  scoreRate));
        } catch (RuntimeException error ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", error.getMessage()));
        }
    }

    ///////////////////////////////////
    // PUT - Mettre à jour un rating
    ///////////////////////////////////
    @Operation(
            summary = "Mettre à jour un rating de série",
            description = "Permet à un utilisateur de modifier la note d'une série qu'il a été déjà évaluée.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating mise à jour avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Rating non trouvée")
            }
    )
    @PutMapping("/serie/{id}/{scoreRate}")
    public ResponseEntity<?> updateRateSerie(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @PathVariable int scoreRate){
        try {
            String responseToken = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(responseToken);

            return ResponseEntity.ok(ratingsService.updateRateSerie(id, email, scoreRate));

        } catch (RuntimeException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", error.getMessage()));
        }
    }
}
