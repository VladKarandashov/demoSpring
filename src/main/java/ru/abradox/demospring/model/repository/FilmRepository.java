package ru.abradox.demospring.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abradox.demospring.model.dto.GenreCount;
import ru.abradox.demospring.model.dto.ItemFilm;
import ru.abradox.demospring.model.entity.Country;
import ru.abradox.demospring.model.entity.Film;

import java.util.List;
import java.util.stream.Stream;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Stream<Film> streamAllBy();
    List<ItemFilm> findAllBy();

    @Query("SELECT id FROM Film ORDER BY id ASC  LIMIT 1")
    Long findFirstRowId();
    @Query("SELECT id FROM Film ORDER BY id DESC LIMIT 1")
    Long findLastRowId();

    @Query("SELECT f.genre as genre, COUNT(*) as count FROM Film f GROUP BY f.genre")
    List<GenreCount> countFilmsGenre();

    @Query("SELECT f.genre as genre, COUNT(*) as count FROM Film f WHERE f.country = :country GROUP BY f.genre")
    List<GenreCount> countFilmsGenreByCountry(@Param("country") Country country);

    @Query("SELECT MAX(id) FROM Film WHERE id < :myId")
    Long getPreviousId(@Param("myId") Long myId);
    @Query("SELECT MIN(id) FROM Film WHERE id > :myId")
    Long getNextId(@Param("myId") Long myId);
}
