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

    public RatingController(RatingsService ratingsService, JwtService jwtService, LoginService loginService, RatingsRepository ratingsRepository){
        this.ratingsService = ratingsService;
        this.jwtService = jwtService;
        this.loginService = loginService;
        this.ratingsRepository = ratingsRepository;
    }

    @GetMapping("/getAllRates")
    public List<Ratings> getAllRates(){
        return ratingsRepository.findAll();
    }

    @GetMapping("/serie/{id}")
    public ResponseEntity<?> getSerieAverage(@PathVariable Long id) {
        return ResponseEntity.ok(ratingsService.getAverageSerieRating(id));
    }


    @GetMapping("/episode/{id}")
    public ResponseEntity<?> getEpisodeAverage(@PathVariable Long id) {
        return ResponseEntity.ok(ratingsService.getAverageEpisodeRating(id));
    }

    // POST
    @PostMapping("/episode/{idEpisode}")
    public ResponseEntity<?> rateEpisode(@RequestHeader("Authorization") String authHeader, @PathVariable Long idEpisode, @RequestBody int score){
        try {
            String responseToken = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(responseToken);

            return ResponseEntity.ok(ratingsService.rateEpisode(idEpisode, email, score));

        } catch (RuntimeException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", error.getMessage()));
        }
    }

    // POST
    @PostMapping("/serie/{id}")
    public ResponseEntity<?> rateSerie(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody int scoreRate){
        try {
            String responseToken = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(responseToken);


            return ResponseEntity.ok(ratingsService.rateSerie(id, email,  scoreRate));
        } catch (RuntimeException error ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", error.getMessage()));
        }
    }

    //Update
    @PutMapping("/episode/{idEpisode}")
    public ResponseEntity<?> updateRateEpisode(@RequestHeader("Authorization") String authHeader, @PathVariable Long idEpisode, @RequestBody int score){
        try {
            String responseToken = jwtService.validateAndReturnToken(authHeader);
            String email = loginService.extractEmail(responseToken);

            return ResponseEntity.ok(ratingsService.updateRateEpisode(idEpisode, email, score));

        } catch (RuntimeException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", error.getMessage()));
        }
    }



}
