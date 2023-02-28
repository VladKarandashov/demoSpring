package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.Quality;

public interface QualityRepository extends JpaRepository<Quality, Long> {
    boolean existsByType(String type);
}
