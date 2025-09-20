package org.example.backend.service;

import org.example.backend.exception.ModelNotFoundException;
import org.example.backend.models.Person;
import org.example.backend.models.Serie;
import org.example.backend.repository.PersonRepository;
import org.example.backend.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final SerieRepository serieRepository;

    public PersonService(PersonRepository personRepository, SerieRepository serieRepository) {
        this.personRepository = personRepository;
        this.serieRepository = serieRepository;
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Person findPersonsByName(String name) {
        return personRepository.findPersonByNom(name);
    }

    public Person findPersonById(Long id){
        return personRepository.findPersonById(id);
    }

    public List<Serie> findHistoryById(Long id){
        Person person = personRepository.findPersonById(id);
        return person.getHistory();
    }

    public Person markSerieAsView(Long idPerson, Long serieId) {

        Person person = personRepository.findPersonById(idPerson);
        Serie seriToAdd = serieRepository.findSerieById(serieId);

        List<Serie> actualSeries = person.getHistory();
        person.setHistory(actualSeries);
        personRepository.save(person);
        return person;
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
