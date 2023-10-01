package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.NewsItem;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {
}
