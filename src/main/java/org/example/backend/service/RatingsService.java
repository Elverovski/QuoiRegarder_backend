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

    // rate une episode
    public String rateEpisode(Long idEpisde, String email, int scoreRate) {
        Episode episode = episodeRepository.findEpisodeById(idEpisde);

        if (episode == null) {
            throw new RuntimeException("Episode introuvable");
        }

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        Ratings isEpisodeAlreadyRate = ratingsRepository.findRatingsByUserIdAndEpisode(user, episode);
        if (isEpisodeAlreadyRate != null) {
            throw new RuntimeException("Vous avez déja ajouté un rate à cette épisode");
        }

        Ratings rateEpisode = new Ratings();
        rateEpisode.setUserId(user);
        rateEpisode.setEpisode(episode);
        rateEpisode.setScore(scoreRate);

        ratingsRepository.save(rateEpisode);
        return "Episode rate avec succès";
    }

    // rate une serie
    public String rateSerie(Long idSerie, String email, int scoreRate) {
        Serie serie = serieRepository.findSerieById(idSerie);
        if (serie == null) {
            throw new RuntimeException("Serie introuvable");
        }

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        Ratings isSerieAlreadyRate = ratingsRepository.findRatingsByUserIdAndSerie(user, serie);
        if (isSerieAlreadyRate != null) {
            throw new RuntimeException("Vous avez déja ajouté un rate à cette serie");
        }

        if (scoreRate > 100 | scoreRate < 0){
            throw new RuntimeException("Score doit être compris entre 0 et 100");
        }

        Ratings rateSerie = new Ratings();
        rateSerie.setUserId(user);
        rateSerie.setSerie(serie);
        rateSerie.setScore(scoreRate);

        ratingsRepository.save(rateSerie);

        Double averageSerie = getAverageSerieRating(idSerie);
        serie.setNote(averageSerie);
        serieRepository.save(serie);
        return "Serie rate avec succès";
    }

    // update le rate d'une serie
    public String updateRateSerie(Long idSerie, String email, int scoreRate) {
        Serie serie = serieRepository.findSerieById(idSerie);

        if (serie == null) {
            throw new RuntimeException("Serie introuvable");
        }

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        Ratings rateSerie = ratingsRepository.findRatingsByUserIdAndSerie(user, serie);

        if (rateSerie == null) {
            throw new RuntimeException("impossible de update ! Rate introuvable");
        }

        if (scoreRate > 100 | scoreRate < 0){
            throw new RuntimeException("Score doit être compris entre 0 et 100");
        }

        rateSerie.setScore(scoreRate);
        ratingsRepository.save(rateSerie);

        Double averageSerie = getAverageSerieRating(idSerie);
        serie.setNote(averageSerie);
        serieRepository.save(serie);
        return "Serie update rate avec succès";
    }

    // Retourne la moyenne des notes d'une série
    public Double getAverageSerieRating(Long serieId) {
        Double averageSerie = ratingsRepository.getAverageSerieRating(serieId);

        if (averageSerie == null) {
            return  0.0;
        }
        return averageSerie;
    }

    public Double getAverageEpisodeRating(Long episodeId) {
        return ratingsRepository.getAverageEpisodeRating(episodeId);
    }
}