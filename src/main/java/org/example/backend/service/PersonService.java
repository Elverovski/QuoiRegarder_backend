package org.example.backend.service;

import org.example.backend.exception.ModelNotFoundException;
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

    // Update
    public Person updatePerson(Person newOne, Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setNom(newOne.getNom());
                    person.setPrenom(newOne.getPrenom());
                    person.setEmail(newOne.getEmail());
                    person.setGenre(newOne.getGenre());
                return personRepository.save(person);
                }).orElseThrow(() -> new ModelNotFoundException(id, "Person"));
    }

    // Delete
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    // Create
    public Person createPerson(Person newPerson) {
        return personRepository.save(newPerson);
    }
    


}
