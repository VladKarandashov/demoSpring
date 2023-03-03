package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByFullName(String fullName);
    Optional<Person> findByFullName(String fullName);
}
