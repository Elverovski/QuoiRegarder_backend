package org.example.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.models.LoginResponse;
import org.example.backend.models.User;
import org.example.backend.models.Serie;
import org.example.backend.service.LoginService;
import org.example.backend.service.UserService;
import org.example.backend.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    public final UserService service;
    public final RecommendationService serviceRecommandation;
    public final LoginService loginService;

    public UserController(UserService service, RecommendationService serviceRecommandation, LoginService loginService) {
        this.service = service;
        this.serviceRecommandation = serviceRecommandation;
        this.loginService = loginService;
    }

    // Get
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/search")
    public User getUserByName(@RequestParam String name) {
        return service.findUsersByName(name);
    }

    @GetMapping("/{id}/history")
    public List<Serie> getHistoryById(@PathVariable Long id){
        return service.findHistoryById(id);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    // Post
    @PostMapping("/{id}/history/{seriesId}")
    public User markSerieAsView(@PathVariable Long id, @PathVariable Long seriesId) {
        return service.markSerieAsView(id, seriesId);
    }

    @GetMapping("/{id}/recommendations")
    public List<Serie> getRecommendation(@PathVariable Long id){
        return serviceRecommandation.getRecommendation(id);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User newUser) {
        return service.createUser(newUser);
    }

    // Put
    @PutMapping("/updateUser/{id}")
    public User updateUser(@RequestBody User newOne, @PathVariable Long id) {
        return service.updateUser(newOne, id);
    }


    // Delete
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PostMapping("/login")
    public LoginResponse signin(@RequestBody User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(loginService.login(user.getEmail(), user.getPassword()));
        return  loginResponse;
    }

}