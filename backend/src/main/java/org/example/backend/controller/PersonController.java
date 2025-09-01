package org.example.backend.controller;

import org.example.backend.models.Person;
import org.example.backend.repository.PersonRepository;
import org.example.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/persons")
@CrossOrigin
public class PersonController {

    public final PersonService service;

    public  PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return service.findAllPersons();
    }
}
