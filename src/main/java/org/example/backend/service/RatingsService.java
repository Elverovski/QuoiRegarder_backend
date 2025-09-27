package org.example.backend.service;

import org.example.backend.models.Ratings;
import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.repository.RatingsRepository;
import org.example.backend.repository.SerieRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingsService {

    private final RatingsRepository ratingsRepository;
    private final UserRepository userRepository;
    private final SerieRepository serieRepository;

    public RatingsService(RatingsRepository ratingsRepository,
                          UserRepository userRepository,
                          SerieRepository serieRepository) {
        this.ratingsRepository = ratingsRepository;
        this.userRepository = userRepository;
        this.serieRepository = serieRepository;
    }

    // créer le login pour pouvoir avoir un test plus simple et précis

    public boolean rateEpisode(Long id, int scoreRate){
        // 1- trouver le user avec la session
        // 2- trouver l'episode
        // 3- créer une new Ratings

        return true;
    }

    public boolean rateSerie(Long id, int scoreRate){
        // 1- trouver le user avec la session
        // 2- trouver la serie
        // 3- créer une new Ratings

        return true;
    }

}
