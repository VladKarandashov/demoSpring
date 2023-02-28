package ru.abradox.demospring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.abradox.demospring.model.dto.GenreCountImpl;
import ru.abradox.demospring.model.entity.Country;
import ru.abradox.demospring.model.repository.CountryRepository;
import ru.abradox.demospring.model.repository.FilmRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/statistic")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StatisticsController {

    private final FilmRepository filmRepository;
    private final CountryRepository countryRepository;

    @GetMapping("")
    public String statistics(@RequestParam(name = "country", required = false) Long id, Model model) throws JsonProcessingException {
        model.addAttribute("total", filmRepository.count());
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);

        if (id != null && id == 0) {
            id = null;
        }
        ObjectMapper objectMapper = new ObjectMapper();

        if (id == null || !countryRepository.existsById(id)) {
            model.addAttribute("title", "Во всех странах");
            String json = objectMapper.writeValueAsString(filmRepository.countFilmsGenre().stream()
                    .map(el -> new GenreCountImpl(el.getGenre(), el.getCount()))
                    .collect(Collectors.toList()));
            log.debug(json);
            model.addAttribute("countArray", json);
        } else {
            Country country = countryRepository.findById(id).get();
            model.addAttribute("title", "В стране: " + country.getTitle());
            String json = objectMapper.writeValueAsString(filmRepository.countFilmsGenreByCountry(country).stream()
                    .map(el -> new GenreCountImpl(el.getGenre(), el.getCount()))
                    .collect(Collectors.toList()));
            log.debug(json);
            model.addAttribute("countArray", json);
        }
        return "statistics";
    }
}
