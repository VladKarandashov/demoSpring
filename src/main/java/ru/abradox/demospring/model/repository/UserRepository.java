package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);
    Optional<User> findByLogin(String login);
}
