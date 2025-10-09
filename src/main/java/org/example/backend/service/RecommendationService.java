package org.example.backend.service;

import org.example.backend.models.User;
import org.example.backend.models.Serie;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final UserRepository userRepository;
    private final SerieRepository serieRepository;

    public RecommendationService(UserRepository userRepository, SerieRepository serieRepository) {
        this.userRepository = userRepository;
        this.serieRepository = serieRepository;
    }

    public List<Serie> getRecommendation(String email) {
        User user = userRepository.findUserByEmail(email);

        if (user == null){
            throw new RuntimeException("Utilisateur introuvable");
        }

        List<Serie> history = user.getHistory();
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
