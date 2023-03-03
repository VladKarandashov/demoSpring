package ru.abradox.demospring.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abradox.demospring.model.entity.Genre;
import ru.abradox.demospring.model.repository.GenreRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GenreRestController {

    private final GenreRepository objectRepository;

    @PostMapping("/miniCreate")
    public CreateResponse<Genre> miniCreate(@RequestBody CreateRequest request) {
        log.debug(request.toString());
        String title = request.getTitle();
        if (title.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByTitle(title)) throw new RuntimeException();
        var genre = objectRepository.save(new Genre(request.getTitle()));
        List<Genre> genres = objectRepository.findAll();
        return new CreateResponse<>(genres, genre);
    }

    @Data
    @ToString
    private static class CreateRequest {
        private String title;
    }

    @Data
    @AllArgsConstructor
    private static class CreateResponse<T> {
        private List<T> list;
        private T selected;
    }
}
