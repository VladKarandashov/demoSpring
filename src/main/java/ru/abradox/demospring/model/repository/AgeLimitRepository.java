package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.AgeLimit;

public interface AgeLimitRepository extends JpaRepository<AgeLimit, Long> {
    boolean existsByCategory(String category);
}
