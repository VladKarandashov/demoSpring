package ru.abradox.demospring.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abradox.demospring.model.dto.CreateRequest;
import ru.abradox.demospring.model.dto.CreateResponse;
import ru.abradox.demospring.model.dto.Item;
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
    public CreateResponse miniCreate(@RequestBody CreateRequest request) {
        log.debug(request.toString());
        String title = request.getTitle();
        if (title.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByTitle(title)) throw new RuntimeException();
        var genre = objectRepository.save(new Genre(request.getTitle()));
        var item = new Item(genre.getId(), genre.getTitle());
        List<Item> genres = objectRepository.findAll().stream().map(el -> new Item(el.getId(), el.getTitle())).toList();
        return new CreateResponse(genres, item);
    }


}
