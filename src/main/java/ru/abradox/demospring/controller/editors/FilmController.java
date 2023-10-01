package ru.abradox.demospring.controller.editors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.abradox.demospring.model.dto.ItemFilmImpl;
import ru.abradox.demospring.model.entity.*;
import ru.abradox.demospring.model.repository.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/film")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FilmController {

    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final AgeLimitRepository ageLimitRepository;
    private final QualityRepository qualityRepository;
    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;

    @GetMapping("/editor")
    public String showEditorForm(@RequestParam(required = false) Long choose, @CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        if (choose != null) return "redirect:/film/editor/" + choose;
        Long firstFilmId = filmRepository.findFirstRowId();
        return "redirect:/film/editor/" + firstFilmId;
    }

    @GetMapping("/editor/{id}")
    public String showEditorFormById(@PathVariable Long id, Model model, @CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        Long firstFilmId = filmRepository.findFirstRowId();
        Long lastFilmId = filmRepository.findLastRowId();
        if (id != 0 && !filmRepository.existsById(id)) return "redirect:/film/editor/" + firstFilmId;

        model.addAttribute("firstId", firstFilmId);
        model.addAttribute("lastId", lastFilmId);

        Long prevId = filmRepository.getPreviousId(id);
        Long nextId = filmRepository.getNextId(id);
        prevId = prevId == null ? lastFilmId : prevId;
        nextId = nextId == null ? firstFilmId : nextId;
        model.addAttribute("prevId", prevId);
        model.addAttribute("nextId", nextId);

        var filmItems = filmRepository.findAllBy().stream().map(el -> new ItemFilmImpl(el.getId(), el.getTitle())).toList();
        model.addAttribute("filmItems", filmItems);

        List<Country> countries = countryRepository.findAll().stream().sorted(Comparator.comparing(Country::getTitle)).toList();
        List<AgeLimit> age_limits = ageLimitRepository.findAll().stream().sorted(Comparator.comparing(AgeLimit::getCategory)).toList();
        List<Genre> genres = genreRepository.findAll().stream().sorted(Comparator.comparing(Genre::getTitle)).toList();
        List<Quality> qualities = qualityRepository.findAll().stream().sorted(Comparator.comparing(Quality::getType)).toList();
        List<Person> persons = personRepository.findAll().stream().sorted(Comparator.comparing(Person::getFullName)).toList();
        model.addAttribute("countries", countries);
        model.addAttribute("age_limits", age_limits);
        model.addAttribute("genres", genres);
        model.addAttribute("qualities", qualities);
        model.addAttribute("persons", persons);

        log.debug("Получение фильма основного:");
        Film film;
        if (id == 0) {
            film = Film.builder()
                    .country(new Country())
                    .genre(new Genre())
                    .ageLimit(new AgeLimit())
                    .quality(new Quality())
                    .build();
            model.addAttribute("personsByFilm", new ArrayList<String>());
        } else {
            film = filmRepository.findById(id).orElseThrow();
            if (film.getCountry() == null) film.setCountry(new Country());
            if (film.getGenre() == null) film.setGenre(new Genre());
            if (film.getAgeLimit() == null) film.setAgeLimit(new AgeLimit());
            if (film.getQuality() == null) film.setQuality(new Quality());
            if (film.getPeople() == null) film.setPeople(new ArrayList<>());
            List<String> namesPersons = film.getPeople().stream().map(Person::toString).toList();
            model.addAttribute("personsByFilm", namesPersons);
            log.debug("Имена людей: "+namesPersons);
        }

        model.addAttribute("film", film);

        return "edit-movie";
    }

    // TODO связанный поиск
}
