package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abradox.demospring.model.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByTitle(String title);
}
