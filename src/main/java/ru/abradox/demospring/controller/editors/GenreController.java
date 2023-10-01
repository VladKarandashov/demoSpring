package ru.abradox.demospring.controller.editors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.entity.Genre;
import ru.abradox.demospring.model.repository.GenreRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/genre")
public class GenreController {
    private final GenreRepository objectRepository;

    public GenreController(GenreRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @GetMapping("/editor")
    public String showEditorForm(@RequestParam(name = "genre", required = false) Long id, Model model, @CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        List<Genre> genres = objectRepository.findAll();
        model.addAttribute("genres", genres);
        Genre genre = (id == null) ? new Genre() : objectRepository.findById(id).orElse(new Genre());
        model.addAttribute("genre", genre);
        return "editors/genre-editor";
    }

    @PostMapping("/create")
    public String create(@RequestBody CreateRequest request) {
        String title = request.getTitle();
        log.debug("Получен запрос на создание {}", title);
        if (title.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByTitle(title)) throw new RuntimeException();
        var genre = objectRepository.save(new Genre(request.getTitle()));
        log.debug("Создан объект {}", genre.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("genre") Genre genre, BindingResult result) {
        log.debug("в update поступил {}", genre);
        if (result.hasErrors() || genre==null || genre.getId()==null || !objectRepository.existsById(genre.getId())) {
            return "redirect:/genre/editor";
        }
        if (genre.getTitle().isBlank()) return "redirect:/genre/editor?genre=" +  genre.getId();
        objectRepository.save(genre);
        return "redirect:/genre/editor?genre=" +  genre.getId();
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("genre") Genre genre, BindingResult result) {
        log.debug("delete {}", genre);
        if (result.hasErrors() || genre == null || genre.getId() == null) {
            return "redirect:/genre/editor";
        }
        if (objectRepository.existsById(genre.getId())) objectRepository.deleteById(genre.getId());
        return "redirect:/genre/editor";
    }

    @Data
    private static class CreateRequest {
        private String title;
    }
}

