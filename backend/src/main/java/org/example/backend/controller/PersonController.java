package org.example.backend.controller;

import org.example.backend.models.Person;
import org.example.backend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // ces fait exprès ne pas mettre de path
    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
