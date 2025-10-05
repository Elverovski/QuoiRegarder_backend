package org.example.backend.repository;

import org.example.backend.models.Episode;
import org.example.backend.models.Ratings;
import org.example.backend.models.Serie;
import org.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    public Ratings findRatingsBySerie(Serie serie);
    public Ratings findRatingsByEpisode(Episode episode);


    @Query("SELECT AVG(r.score) FROM Ratings r WHERE r.serie.id = :serieId")
    Double getAverageSerieRating(@Param("serieId") Long serieId);

    @Query("SELECT AVG(r.score) FROM Ratings r WHERE r.episode.id = :episodeId")
    Double getAverageEpisodeRating(@Param("episodeId") Long episodeId);
    }
