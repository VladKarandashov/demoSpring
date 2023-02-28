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
import ru.abradox.demospring.controller.rest.SearchRestController;
import ru.abradox.demospring.model.repository.FilmRepository;

@Slf4j
@Controller
@RequestMapping("/search")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchController {

    private final FilmRepository filmRepository;
    private final SearchRestController searchRestController;

    @GetMapping()
    public String searchHTML(Model model) {

        var films = filmRepository.findAll();
        model.addAttribute("films", films);

        model.addAttribute("items", searchRestController.getItems(null, null, null));

        return "/search";
    }
}
