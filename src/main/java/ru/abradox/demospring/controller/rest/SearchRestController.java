package ru.abradox.demospring.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abradox.demospring.model.dto.ItemsDTO;
import ru.abradox.demospring.model.entity.AgeLimit;
import ru.abradox.demospring.model.entity.Country;
import ru.abradox.demospring.model.entity.Film;
import ru.abradox.demospring.model.entity.Genre;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchRestController {

    private final FilmRestController filmRestController;

    @GetMapping("/items")
    public ItemsDTO getItems(@RequestParam(required = false) Long genre,
                             @RequestParam(required = false) Long country,
                             @RequestParam(required = false) Long ageLimit)
    {
        List<Film> resultFilmsItems = filmRestController.searchFilms(genre, country, ageLimit);
        Set<Genre> genreSet = new HashSet<>();
        Set<Country> countrySet = new HashSet<>();
        Set<AgeLimit> ageLimitSet = new HashSet<>();
        resultFilmsItems.forEach(el -> {
            genreSet.add(el.getGenre());
            countrySet.add(el.getCountry());
            ageLimitSet.add(el.getAgeLimit());
        });
        var res = ItemsDTO.builder()
                .genreList(genreSet.stream().sorted(Comparator.comparingLong(Genre::getId)).toList())
                .countryList(countrySet.stream().sorted(Comparator.comparingLong(Country::getId)).toList())
                .ageLimitList(ageLimitSet.stream().sorted(Comparator.comparingLong(AgeLimit::getId)).toList())
                .build();
        log.debug(String.valueOf(res));
        return res;

    }
}

