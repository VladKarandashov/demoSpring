package ru.abradox.demospring.controller.editors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/film")
public class FilmController {
    @GetMapping("/editor")
    public String showEditorForm() {
        return "edit-movie";
    }
}
