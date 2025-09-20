package org.example.backend.controller;

import org.example.backend.models.Person;
import org.example.backend.models.Serie;
import org.example.backend.repository.PersonRepository;
import org.example.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@CrossOrigin
public class PersonController {

    public final PersonService service;

    public  PersonController(PersonService service) {
        this.service = service;
    }

    // Get
    @GetMapping("/getAllPersons")
    public List<Person> getAllPersons() {
        return service.findAllPersons();
    }

    @GetMapping("/search")
    public Person getPersonByName(@RequestParam String name) {
        return service.findPersonsByName(name);
    }

    @GetMapping("/{id}/history")
    public List<Serie> getHistoryById(@PathVariable Long id){
        return service.findHistoryById(id);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return service.findPersonById(id);
    }

    // Post
    @PostMapping("/{id}/history/{seriesId}")
    public Person markSerieAsView(@PathVariable Long id, @PathVariable Long seriesId) {
        return service.markSerieAsView(id, seriesId);
    }


    @PostMapping("/createPerson")
    public Person createPerson(@RequestBody Person newPerson) {
        return service.createPerson(newPerson);
    }

    // Put
    @PutMapping("/updatePerson/{id}")
    public Person updatePerson(@RequestBody Person newOne, @PathVariable Long id) {
        return service.updatePerson(newOne, id);
    }


    // Delete
    @DeleteMapping("/deletePerson/{id}")
    public void deletePerson(@PathVariable Long id) {
        service.deletePerson(id);
    }


}