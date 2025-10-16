package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.example.backend.models.Serie;
import org.example.backend.service.JwtService;
import org.example.backend.service.LoginService;
import org.example.backend.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Series", description = "Endpoints pour la gestion des series)")
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

    ///////////////////////////////////
    // GET - Toutes les series
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir toutes les series",
            description = "Retourne la liste complete des series disponibles. "
                    + "L'utilisateur doit être authentifié pour accéder à cette ressource.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des series récupérée avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/getAllSeries")
    public ResponseEntity<?> getAllSeries(@RequestHeader("Authorization") String authHeader){
        jwtService.validateToken(authHeader);
        return ResponseEntity.ok(service.findAllSeries());
    }


    ///////////////////////////////////
    // GET - Series tendances
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir les series tendances",
            description = "Retourne la liste des series en tendance. "
                    + "L'utilisateur doit être authentifié pour accéder à cette ressource.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des series en tendance récupérée avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/getAllTendances")
    public List<Serie> getAllTendances(@RequestHeader("Authorization") String authHeader){
        jwtService.validateToken(authHeader);
        return service.getAllTendances();
    }

    ///////////////////////////////////
    // GET - Serie par titre
    ///////////////////////////////////
    @Operation(
            summary = "Rechercher une serie par son titre",
            description = "Permet de retrouver une serie par son nom."
                    + "L'utilisateur doit être authentifié pour accéder à cette ressource.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serie trouvée"),
                    @ApiResponse(responseCode = "404", description = "Serie non trouvée"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/getSerieTitre")
    public Serie getSerieByTitre(@RequestParam String name, @RequestHeader("Authorization") String authHeader){
        jwtService.validateToken(authHeader);
        return service.findSeriesByName(name);
    }


    ///////////////////////////////////
    // GET - Serie par ID
    ///////////////////////////////////
    @Operation(
            summary = "Obtenir une serie par son ID",
            description = "Retourne les informations d'une serie spécifique."
                    + "L'utilisateur doit être authentifié pour accéder à cette ressource.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serie trouvée"),
                    @ApiResponse(responseCode = "404", description = "Serie non trouvée"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/{id}")
    public Serie getSerieById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader){
        // a compléter
        jwtService.validateToken(authHeader);
        return service.findSeriesById(id);
    }

    ///////////////////////////////////
    // GET - Recherche specifique
    ///////////////////////////////////
    @Operation(
            summary = "Rechercher des series par genre ou nombre d'épisodes",
            description = "Permet de filtrer les series par genre et/ou par nombre d'épisodes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Résultats de la recherche retournés avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
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

    ///////////////////////////////////
    // POST - Créer une serie
    ///////////////////////////////////
    @Operation(
            summary = "Créer une nouvelle serie",
            description = "Ajoute une nouvelle serie.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Serie créée avec succès")
            }
    )
    @PostMapping("/createSerie")
    public Serie createSerie(@RequestBody Serie newSerie){
        return service.createSerie(newSerie);
    }

    ///////////////////////////////////
    // PUT - Mettre à jour une serie
    ///////////////////////////////////
    @Operation(
            summary = "Mettre à jour une serie existante",
            description = "Permet de modifier les informations d'une série specifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serie mise à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Serie non trouvée")
            }
    )
    @PutMapping("/updateSerie/{id}")
    public Serie updateSerie(@RequestBody Serie newOne, @PathVariable Long id){
        return service.updateSerie(newOne, id);
    }

    ///////////////////////////////////
    // DELETE - Supprimer une serie
    ///////////////////////////////////
    @Operation(
            summary = "Supprimer une serie",
            description = "Permet de supprimer une serie specifique.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Serie supprimée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Serie non trouvée")
            }
    )
    @DeleteMapping("/deleteSerie/{id}")
    public void deleteSerie(@PathVariable Long id){
        service.deleteSerie(id);
    }



}

