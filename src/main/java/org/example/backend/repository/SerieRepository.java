package org.example.backend.repository;

import org.example.backend.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    public List<Serie> findSerieByTitre(String titre);
}
