package org.example.backend.service;

import org.example.backend.models.Person;
import org.example.backend.models.Serie;
import org.example.backend.repository.PersonRepository;
import org.example.backend.repository.SerieRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final PersonRepository personRepository;
    private final SerieRepository serieRepository;

    public RecommendationService(PersonRepository personRepository, SerieRepository serieRepository) {
        this.personRepository = personRepository;
        this.serieRepository = serieRepository;
    }

    public List<Serie> getRecommendation(Long idPerson) {
        Person person = personRepository.findPersonById(idPerson);

        List<Serie> history = person.getHistory();
        if (history == null) {
            history = new ArrayList<Serie>();
        }

        List<String> genreVus = new ArrayList<>();

        // Touts les genres vu par user
        for (Serie serie : history){
            if (!genreVus.contains(serie.getGenre())){
                genreVus.add(serie.getGenre());
            }
        }

        List<Serie> recommendations = new ArrayList<>();

        for (String genre : genreVus) {
            List<Serie> serieGenre = serieRepository.findSerieByGenre(genre);
            int count = 0;

            for (Serie serie : serieGenre) {
                if (!recommendations.contains(serie) && count < 3){
                    recommendations.add(serie);
                    count++;
                }
            }
        }

        return recommendations;
    }
}
