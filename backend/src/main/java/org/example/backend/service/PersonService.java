package org.example.backend.service;

import org.example.backend.models.Person;
import org.example.backend.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> findPersonsByName(String name) {
        return personRepository.findPersonByNom(name);
    }
    


}
