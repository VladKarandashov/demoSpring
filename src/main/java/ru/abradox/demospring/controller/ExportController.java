package ru.abradox.demospring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.abradox.demospring.model.repository.FilmRepository;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExportController {

    private final FilmRepository filmRepository;

    @GetMapping("/export")
    public String getHelpHTML(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        return "/export";
    }
}
