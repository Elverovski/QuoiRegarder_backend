package org.example.backend.repository;

import org.example.backend.models.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    public Episode findEpisodeById(Long id);
}
