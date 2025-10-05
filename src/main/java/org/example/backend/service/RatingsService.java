package org.example.backend.service;

import org.example.backend.models.Ratings;
import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.example.backend.models.Episode;
import org.example.backend.repository.EpisodeRepository;
import org.example.backend.repository.RatingsRepository;
import org.example.backend.repository.SerieRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingsService {

    private final RatingsRepository ratingsRepository;
    private final UserRepository userRepository;
    private final SerieRepository serieRepository;
    private final EpisodeRepository episodeRepository;


    public RatingsService(RatingsRepository ratingsRepository,
                          UserRepository userRepository,
                          SerieRepository serieRepository,
                          EpisodeRepository episodeRepository) {
        this.ratingsRepository = ratingsRepository;
        this.userRepository = userRepository;
        this.serieRepository = serieRepository;
        this.episodeRepository = episodeRepository;
    }

    // créer le login pour pouvoir avoir un test plus simple et précis

    public String rateEpisode(Long idEpisde, String email, int scoreRate){
        Episode episode = episodeRepository.findEpisodeById(idEpisde);

        if (episode == null) {
            throw new RuntimeException("Episod introuvable");
        }

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        Ratings rateEpisode = new Ratings();
        rateEpisode.setUserId(user);
        rateEpisode.setEpisode(episode);
        rateEpisode.setScore(scoreRate);

        ratingsRepository.save(rateEpisode);
        return "Episode rate avec succès";
    }

    public String rateSerie(Long idSerie, String email, int scoreRate){
        Serie serie = serieRepository.findSerieById(idSerie);
        if (serie == null){
            throw new RuntimeException("Serie introuvable pe");
        };

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        Ratings rateSerie = new Ratings();
        rateSerie.setUserId(user);
        rateSerie.setSerie(serie);
        rateSerie.setScore(scoreRate);

        ratingsRepository.save(rateSerie);
        return "Serie rate avec succès";
    }

}
