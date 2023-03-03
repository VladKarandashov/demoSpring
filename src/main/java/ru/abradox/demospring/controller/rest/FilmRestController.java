package ru.abradox.demospring.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.dto.CreateRequest;
import ru.abradox.demospring.model.dto.MiniFilmDTO;
import ru.abradox.demospring.model.entity.Film;
import ru.abradox.demospring.model.repository.FilmRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostMapping("/save")
    public Film saveFilm(@RequestBody MiniFilmDTO request) {
        String title = request.getTitle();
        if (title.isBlank()) throw new RuntimeException();

        Film film;
        if (request.getId() == null) {
            film = new Film();
        } else {
            film = filmRepository.findById(request.getId()).orElseThrow();
        }

        filmRepository.save(film);
        return null;
    }

    @GetMapping
    public List<Film> getAllFilms(){
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
                             @RequestParam(required = false) Long ageLimit)
    {
        try (Stream<Film> films = filmRepository.streamAllBy()) {
            return films
                    .filter(el -> genre == null || Objects.equals(el.getGenre().getId(), genre))
                    .filter(el -> country == null || Objects.equals(el.getCountry().getId(), country))
                    .filter(el -> ageLimit == null || Objects.equals(el.getAgeLimit().getId(), ageLimit))
                    .collect(Collectors.toList());
        }
    }
}
