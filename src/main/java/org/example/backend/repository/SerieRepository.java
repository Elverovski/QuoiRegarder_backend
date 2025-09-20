package org.example.backend.repository;

import org.example.backend.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    public Serie findSerieByTitre(String titre);
    public List<Serie> findSerieByGenre(String genre);
    public List<Serie> findSerieByGenreAndNbepisodes(String genre, int NbEpisodes);
    public List<Serie> findSerieByNbepisodesGreaterThanEqual(int NbEpisodes);
}
