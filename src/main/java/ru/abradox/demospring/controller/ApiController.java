package ru.abradox.demospring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.abradox.demospring.controller.rest.SearchRestController;

@Controller
@RequiredArgsConstructor
public class ApiController {

    private final SearchRestController searchRestController;

    @GetMapping("/api")
    public String getApiHTML(Model model) {
        model.addAttribute("items", searchRestController.getItems(null, null, null));
        return "api";
    }
}
