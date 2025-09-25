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

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUsersByName(String name) {
        return userRepository.findUserByNom(name);
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id);
    }

    public List<Serie> findHistoryById(Long id){
        User user = userRepository.findUserById(id);
        return user.getHistory();
    }

    public User markSerieAsView(Long idUser, Long serieId) {

        User user = userRepository.findUserById(idUser);
        Serie seriToAdd = serieRepository.findSerieById(serieId);

        List<Serie> actualSeries = user.getHistory();
        actualSeries.add(seriToAdd);
        user.setHistory(actualSeries);
        userRepository.save(user);
        return user;
    }




    // Update
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

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Create
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }


    


}
