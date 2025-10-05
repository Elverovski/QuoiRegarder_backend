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
    // GET: obtenir tous les utilisateurs
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    // GET: obtenir un utilisateur par son Nom
    public User findUsersByName(String name) {
        return userRepository.findUserByNom(name);
    }

    // GET: obtenir un utilisataeur par son ID
    public User findUserById(Long id){
        return userRepository.findUserById(id);
    }

    // GET: obtenir le historique d'un utilistaeur par son ID
    public List<Serie> findHistoryById(Long id){
        User user = userRepository.findUserById(id);
        return user.getHistory();
    }
    // PUT:
    public User markSerieAsView(Long idUser, Long serieId) {

        User user = userRepository.findUserById(idUser);
        Serie seriToAdd = serieRepository.findSerieById(serieId);

        List<Serie> actualSeries = user.getHistory();
        actualSeries.add(seriToAdd);
        user.setHistory(actualSeries);
        userRepository.save(user);
        return user;
    }

    // PUT: update les donnes d'un utilistaeur
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

    // DELETE: effacer un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // POST: creer un nouveau utilisateur
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }


    


}
