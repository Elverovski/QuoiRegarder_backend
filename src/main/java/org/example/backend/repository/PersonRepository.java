package org.example.backend.repository;

import org.example.backend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findPersonByNom(String name);


}
