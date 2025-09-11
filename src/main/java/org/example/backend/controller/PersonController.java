package org.example.backend.controller;

import org.example.backend.models.Person;
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

    @GetMapping("/getAllPersons")
    public List<Person> getAllPersons() {
        return service.findAllPersons();
    }

    @GetMapping("/search")
    public List<Person> getPersonByName(@RequestParam String name) {
        return service.findPersonsByName(name);
    }

    @PostMapping("/createPerson")
    public Person createPerson(@RequestBody Person newPerson) {
        return service.createPerson(newPerson);
    }

    @PutMapping("/updatePerson/{id}")
    public Person updatePerson(@RequestBody Person newOne, @PathVariable Long id) {
        return service.updatePerson(newOne, id);
    }

    @DeleteMapping("/deletePerson/{id}")
    public void deletePerson(@PathVariable Long id) {
        service.deletePerson(id);
    }


}