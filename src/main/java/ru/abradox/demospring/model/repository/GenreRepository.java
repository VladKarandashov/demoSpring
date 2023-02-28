package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByTitle(String title);
}
