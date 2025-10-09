package org.example.backend.controller;

import org.apache.coyote.Response;
import org.example.backend.models.Serie;
import org.example.backend.service.JwtService;
import org.example.backend.service.LoginService;
import org.example.backend.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serie")
@CrossOrigin
public class SerieController {

    public final SerieService service;
    private final LoginService loginService;
    private final JwtService jwtService;


    public SerieController(SerieService service, LoginService loginService, JwtService jwtService) {
        this.service = service;
        this.loginService = loginService;
        this.jwtService = jwtService;
    }

    // Get
        @GetMapping("/getAllSeries")
    public ResponseEntity<?> getAllSeries(@RequestHeader("Authorization") String authHeader){
        // validation du token sans se reconnecter
        //if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //return ResponseEntity.status(401).body("Token manquant");
        //}
        //String token = authHeader.substring(7);
        //if (!loginService.isTokenValid(token)){
        //    return ResponseEntity.status(401).body("Token invalide ou expiré");
        //}

        jwtService.validateToken(authHeader);
        return ResponseEntity.ok(service.findAllSeries());
    }

    @GetMapping("/getALlTendances")
    public List<Serie> getAllTendances(@RequestHeader("Authorization") String authHeader){
        jwtService.validateToken(authHeader);
        return ResponseEntity.ok(service.getAllTendances()).getBody();
    }

    @GetMapping("/getSerieTitre")
    public Serie getSerieByTitre(@RequestParam String name, @RequestHeader("Authorization") String authHeader){
        jwtService.validateToken(authHeader);
        return service.findSeriesByName(name);
    }

    @GetMapping("/{id}")
    public Serie getSerieById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader){
        // a compléter
        jwtService.validateToken(authHeader);
        return service.findSeriesById(id);
    }

    @GetMapping("/searchSerie")
    public List<Serie> searchSerie(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false, defaultValue = "0") int nbEpisodes,
            @RequestHeader("Authorization") String authHeader) {
        jwtService.validateToken(authHeader);

        if ((genre == null || genre.isEmpty()) && nbEpisodes > 0) {
            return service.searchSerie(nbEpisodes);
        } else if ((genre != null && !genre.isEmpty()) && nbEpisodes <= 0) {
            return service.searchSerie(genre);
        } else if ((genre != null && !genre.isEmpty()) && nbEpisodes > 0) {
            return service.searchSerie(genre, nbEpisodes);
        } else {
            return service.findAllSeries();
        }
    }

    // Post
    @PostMapping("/createSerie")
    public Serie createSerie(@RequestBody Serie newSerie){
        return service.createSerie(newSerie);
    }

    // Put
    @PutMapping("/updateSerie/{id}")
    public Serie updateSerie(@RequestBody Serie newOne, @PathVariable Long id){
        return service.updateSerie(newOne, id);
    }

    // Delete
    @DeleteMapping("/deleteSerie/{id}")
    public void deleteSerie(@PathVariable Long id){
        service.deleteSerie(id);
    }



}

