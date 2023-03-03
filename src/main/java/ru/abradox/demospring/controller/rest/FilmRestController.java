package ru.abradox.demospring.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.dto.MiniFilmDTO;
import ru.abradox.demospring.model.entity.Film;
import ru.abradox.demospring.model.entity.Genre;
import ru.abradox.demospring.model.entity.Person;
import ru.abradox.demospring.model.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/film")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FilmRestController {

    private final FilmRepository filmRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final AgeLimitRepository ageLimitRepository;
    private final QualityRepository qualityRepository;
    private final PersonRepository personRepository;

    @PostMapping("/save")
    public Film saveFilm(@RequestBody MiniFilmDTO request) {
        log.debug("Запрос на сохранение");
        log.debug(request.toString());
        if (request.getTitle().isBlank())
            throw new RuntimeException();
        if (request.getCountry() != 0 && !countryRepository.existsById(request.getCountry()))
            throw new RuntimeException();
        if (request.getGenre() != 0 && !genreRepository.existsById(request.getGenre()))
            throw new RuntimeException();
        if (request.getAgeLimit() != 0 && !ageLimitRepository.existsById(request.getAgeLimit()))
            throw new RuntimeException();
        if (request.getQuality() != 0 && !qualityRepository.existsById(request.getQuality()))
            throw new RuntimeException();

        Film film = Film.builder()
                .id(request.getId() == null ? null : request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .totalBoxOffice(request.getTotalBoxOffice())
                .budget(request.getBudget())
                .releaseDate(request.getReleaseDate())
                .country(request.getCountry() == 0 ? null : countryRepository.findById(request.getCountry()).orElseThrow())
                .genre(request.getGenre() == 0 ? null : genreRepository.findById(request.getGenre()).orElseThrow())
                .ageLimit(request.getAgeLimit() == 0 ? null : ageLimitRepository.findById(request.getAgeLimit()).orElseThrow())
                .quality(request.getQuality() == 0 ? null : qualityRepository.findById(request.getQuality()).orElseThrow())
                .build();
        List<Person> persons = new ArrayList<>();
        request.getPeople().forEach(el -> persons.add(personRepository.findByFullName(el).orElseThrow()));
        film.setPeople(persons);
        return filmRepository.save(film);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeleteRequest request) {
        Long id = request.getId();
        if (id == null || !filmRepository.existsById(id)) {
            throw new RuntimeException();
        }
        filmRepository.deleteById(id);
    }

    @Data
    private  static class DeleteRequest {
        Long id;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> export() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String films = objectMapper.writeValueAsString(getAllFilms());
        ByteArrayResource resource = new ByteArrayResource(films.getBytes());

        var currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        String currentDateTimeString = currentDateTime.format(formatter);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "films " + currentDateTimeString + ".json")
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @GetMapping("/search")
    @Transactional
    public List<Film> searchFilms(@RequestParam(required = false) Long genre,
                                  @RequestParam(required = false) Long country,
                                  @RequestParam(required = false) Long ageLimit) {
        try (Stream<Film> films = filmRepository.streamAllBy()) {
            return films
                    .filter(el -> genre == null || Objects.equals(el.getGenre().getId(), genre))
                    .filter(el -> country == null || Objects.equals(el.getCountry().getId(), country))
                    .filter(el -> ageLimit == null || Objects.equals(el.getAgeLimit().getId(), ageLimit))
                    .collect(Collectors.toList());
        }
    }
}
