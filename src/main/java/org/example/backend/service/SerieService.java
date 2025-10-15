package org.example.backend.service;

import org.example.backend.exception.ModelNotFoundException;
import org.example.backend.models.Serie;
import org.example.backend.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    // Récupère toutes les séries
    public List<Serie> findAllSeries() {
        return serieRepository.findAll();
    }

    // Récupère une série par son titre
    public Serie findSeriesByName(String titre) {
        return serieRepository.findSerieByTitre(titre);
    }

    // Récupère une série par son id
    public Serie findSeriesById(Long id) {
        return serieRepository.findSerieById(id);
    }

    // Met à jour les informations d'une série existante
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

    // Supprime une série
    public void deleteSerie(Long id) {
        serieRepository.deleteById(id);
    }

    // Crée une nouvelle série
    public Serie createSerie(Serie newSerie) {
        return serieRepository.save(newSerie);
    }

    // Recherche une série par genre et nombre d'épisodes exact
    public List<Serie> searchSerie(String genre, int nbEpisodes){
        return serieRepository.findSerieByGenreAndNbepisodes(genre, nbEpisodes);
    }

    // Recherche une série par genre
    public List<Serie> searchSerie(String genre) {
        return serieRepository.findSerieByGenre(genre);
    }

    // Recherche une série avec un nombre d'épisodes minimum
    public List<Serie> searchSerie(int nbepisodes) {
        return serieRepository.findSerieByNbepisodesGreaterThanEqual(nbepisodes);
    }

    // Récupère les 10 séries les plus tendance (meilleures notes)
    public List<Serie> getAllTendances(){
        List<Serie> tendanceSerie = new ArrayList<>();
        int compteur = 0;

        for (Serie serie : serieRepository.findAllByOrderByNoteDesc()){
            compteur++;
            if (compteur < 11) {
                tendanceSerie.add(serie);
            }
        }

        return tendanceSerie;
    }
}