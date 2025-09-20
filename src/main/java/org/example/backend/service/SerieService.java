package org.example.backend.service;

import org.example.backend.exception.ModelNotFoundException;
import org.example.backend.models.Serie;
import org.example.backend.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {
    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    // GET
    public List<Serie> findAllSeries() {
        return serieRepository.findAll();
    }

    public Serie findSeriesByName(String titre) {
        return serieRepository.findSerieByTitre(titre);
    }

    public Serie findSeriesById(Long id) {
        return serieRepository.findSerieById(id);
    }

    // Update
    public Serie updateSerie(Serie newOne, Long id) {
        return serieRepository.findById(id)
                .map(serie -> {
                    serie.setTitre(serie.getTitre());
                    serie.setGenre(serie.getGenre());
                    serie.setNbepisodes(serie.getNbepisodes());
                    serie.setNote(serie.getNote());
                    return serieRepository.save(serie);
                }).orElseThrow(() -> new ModelNotFoundException(id, "Serie"));
    }

    // Delete
    public void deleteSerie(Long id) {
        serieRepository.deleteById(id);
    }

    // Create
    public Serie createSerie(Serie newSerie) {
        return serieRepository.save(newSerie);
    }


    public List<Serie> searchSerie(String genre, int nbEpisodes){
        return serieRepository.findSerieByGenreAndNbepisodes(genre, nbEpisodes);
    }

    public List<Serie> searchSerie(String genre) {
        return serieRepository.findSerieByGenre(genre);
    }

    public List<Serie> searchSerie(int nbepisodes) {
        return serieRepository.findSerieByNbepisodesGreaterThanEqual(nbepisodes);
    }

}
