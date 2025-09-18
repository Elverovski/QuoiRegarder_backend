package org.example.backend.repository;

import org.example.backend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    public List<Person> findSerieByNom(String nom);

    List<Person> findPersonByNom(String name);
}
