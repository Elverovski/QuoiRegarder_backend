package org.example.backend.service;

import org.example.backend.exception.ModelNotFoundException;
import org.example.backend.models.User;
import org.example.backend.models.Serie;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SerieRepository serieRepository;

    public UserService(UserRepository userRepository, SerieRepository serieRepository) {
        this.userRepository = userRepository;
        this.serieRepository = serieRepository;
    }

    // Récupère tous les utilisateurs
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Récupère un utilisateur par son nom
    public User findUsersByName(String name) {
        return userRepository.findUserByNom(name);
    }

    // Récupère un utilisateur par son id
    public User findUserById(Long id)
    {
        try {
            return userRepository.findUserById(id);
        } catch (Exception e) {
            System.out.println("ID invalide");
        }
        return null;
    }

    // Récupère l'historique des séries d'un utilisateur via son email
    public List<Serie> findHistoryByEmail(String email){
        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        return user.getHistory();
    }

    // ajouter une série à l'historique de l'utilisateur
    public String markSerieAsView(String email, Long serieId) {

        User user = userRepository.findUserByEmail(email);
        Serie seriToAdd = serieRepository.findSerieById(serieId);

        for (Serie serie : user.getHistory()) {
            if (serie.getId().equals(serieId)) {
                throw new RuntimeException("Série déjà présente dans l'historique");
            }
        }

        List<Serie> actualSeries = user.getHistory();
        actualSeries.add(seriToAdd);
        user.setHistory(actualSeries);
        userRepository.save(user);
        return "serie a été marqué comme vue";
    }

    // Met à jour les informations d'un utilisateur existant
    public User updateUser(User newOne, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNom(newOne.getNom());
                    user.setPrenom(newOne.getPrenom());
                    user.setEmail(newOne.getEmail());
                    user.setGenre(newOne.getGenre());
                return userRepository.save(user);
                }).orElseThrow(() -> new ModelNotFoundException(id, "User"));
    }

    // Supprime un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Crée un nouvel utilisateur
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }
}
