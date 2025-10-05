package org.example.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.models.User;
import org.example.backend.models.Serie;
import org.example.backend.service.JwtService;
import org.example.backend.service.UserService;
import org.example.backend.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    public final UserService service;
    public final RecommendationService serviceRecommandation;
    private final JwtService jwtService;


    public UserController(UserService service, RecommendationService serviceRecommandation, JwtService jwtService) {
        this.service = service;
        this.serviceRecommandation = serviceRecommandation;
        this.jwtService = jwtService;
    }

    // Get
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        jwtService.validateToken(authHeader);;
        return ResponseEntity.ok(service.findAllUsers());
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


}