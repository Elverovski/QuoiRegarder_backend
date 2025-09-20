package org.example.backend.repository;

import org.example.backend.models.Person;
import org.example.backend.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    public Person findPersonByNom(String name);
    public Person findPersonById(Long id);
}
