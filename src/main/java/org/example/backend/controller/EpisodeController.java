package org.example.backend.controller;

import org.example.backend.models.Episode;
import org.example.backend.repository.EpisodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/episodes")
public class EpisodeController {

    private final EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEpisodeById(@PathVariable Long id) {
        try {
            Episode episode = episodeRepository.findEpisodeById(id);

            if (episode == null){
                throw new RuntimeException("Episode introuvable");
            }

            return ResponseEntity.ok(episode);

        } catch (RuntimeException error) {
            return ResponseEntity.status(404).body(Map.of("error", error.getMessage()));
        }
    }
}
