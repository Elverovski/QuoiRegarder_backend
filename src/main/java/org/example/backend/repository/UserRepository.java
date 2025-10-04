package org.example.backend.repository;

import org.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByNom(String name);
    public User findUserById(Long id);
    public User findUserByEmail(String email);
}
