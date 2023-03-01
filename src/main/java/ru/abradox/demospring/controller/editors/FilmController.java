package ru.abradox.demospring.controller.editors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.abradox.demospring.model.dto.ItemFilmImpl;
import ru.abradox.demospring.model.entity.*;
import ru.abradox.demospring.model.repository.*;

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
    public String showEditorForm() {
        Long firstFilmId = filmRepository.findFirstRowId();
        return "redirect:/film/editor/" +  firstFilmId;
    }

    @GetMapping("/editor/{id}")
    public String showEditorFormById(@PathVariable Long id, Model model) {
        Long firstFilmId = filmRepository.findFirstRowId();
        Long lastFilmId = filmRepository.findLastRowId();
        if (id != 0 && !filmRepository.existsById(id)) return  "redirect:/film/editor/" +  firstFilmId;

        model.addAttribute("firstId", firstFilmId);
        model.addAttribute("lastId", lastFilmId);

        Long prevId = filmRepository.getPreviousId(id);
        Long nextId = filmRepository.getNextId(id);
        prevId = prevId==null ? lastFilmId : prevId;
        nextId = nextId==null ? firstFilmId : nextId;
        model.addAttribute("prevId", prevId);
        model.addAttribute("nextId", nextId);

        var filmItems = filmRepository.findAllBy().stream().map(el -> new ItemFilmImpl(el.getId(), el.getTitle())).toList();
        model.addAttribute("filmItems", filmItems);

        List<Country> countries = countryRepository.findAll();
        List<AgeLimit> age_limits = ageLimitRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Quality> qualities = qualityRepository.findAll();
        List<Person> persons = personRepository.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("age_limits", age_limits);
        model.addAttribute("genres", genres);
        model.addAttribute("qualities", qualities);
        model.addAttribute("persons", persons);

        log.debug("Получение фильма основного:");
        Film film;
        if (id==0) film = new Film();
        else film = filmRepository.findById(id).orElseThrow();
        model.addAttribute("film", film);

        return "edit-movie";
    }
}
