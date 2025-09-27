package org.example.backend.repository;

import org.example.backend.models.Episode;
import org.example.backend.models.Ratings;
import org.example.backend.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    public Ratings findRatingsBySerie(Serie serie);
    public Ratings findRatingsByEpisode(Episode episode);
}
