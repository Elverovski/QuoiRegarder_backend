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
    // GET: permet d'obtenir tous les utilisateurs
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    // GET: permet d'obtenir un utilisateur par son Nom
    public User findUsersByName(String name) {
        return userRepository.findUserByNom(name);
    }

    // GET: permet d'obtenir un utilisataeur par son ID
    public User findUserById(Long id)
    {
        try {
            return userRepository.findUserById(id);
        } catch (Exception e) {
            System.out.println("ID invalide");
        }
        return null;
    }

    // GET: permet d'obtenir le historique d'un utilistaeur par son ID
    public List<Serie> findHistoryById(Long id){
        try{
            User user = userRepository.findUserById(id);
            return user.getHistory();

        } catch (Exception e) {
            System.out.println("ID invalide");
        }
        return null;
    }
    // GET: obtenir le historique d'un utilistaeur par son ID
    public List<Serie> findHistoryByEmail(String email){
        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        return user.getHistory();
    }

    // GET: obtenir le historique d'un utilistaeur par son ID
    public List<Serie> findHistoryByEmail(String email){
        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        return user.getHistory();
    }
    // PUT:
    public String markSerieAsView(String email, Long serieId) {

        User user = userRepository.findUserByEmail(email);
        Serie seriToAdd = serieRepository.findSerieById(serieId);

        for (Serie serie : user.getHistory()){
            if (serie.getId() == serieId){
                throw new RuntimeException("Serie existe déja dans history");
            }
        }

        List<Serie> actualSeries = user.getHistory();
        actualSeries.add(seriToAdd);
        user.setHistory(actualSeries);
        userRepository.save(user);
        return "serie a été marqué comme vue";
    }

    // PUT: permet d'update les donnes d'un utilistaeur
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

    // DELETE: permet d'effacer un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // POST: permet de creer un nouveau utilisateur
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }


    


}
