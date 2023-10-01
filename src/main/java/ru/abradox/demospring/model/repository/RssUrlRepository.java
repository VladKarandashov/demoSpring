package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.RssUrl;

public interface RssUrlRepository extends JpaRepository<RssUrl, Long> {
    boolean existsById(Long id);
}
