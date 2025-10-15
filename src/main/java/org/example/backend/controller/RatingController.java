package org.example.backend.controller;

import io.jsonwebtoken.Jwt;
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

    ///////////////////// GET ///////////////////////////////////

    // Récupère toutes les rates
    @GetMapping("/getAllRates")
    public List<Ratings> getAllRates(){
        return ratingsRepository.findAll();
    }

    // Récupère la moyenne de ratings d'une serie
    @GetMapping("/serie/{id}")
    public ResponseEntity<?> getSerieAverage(@PathVariable Long id) {
        return ResponseEntity.ok(ratingsService.getAverageSerieRating(id));
    }

    // Récupère la moyenne de ratings d'une episode
    @GetMapping("/episode/{id}")
    public ResponseEntity<?> getEpisodeAverage(@PathVariable Long id) {
        return ResponseEntity.ok(ratingsService.getAverageEpisodeRating(id));
    }

    ///////////////////// POST ///////////////////////////////////

    // ajouter un score a une episode
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

    // ajouter un score a une serie
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

    // modifier le rate d'une serie
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
