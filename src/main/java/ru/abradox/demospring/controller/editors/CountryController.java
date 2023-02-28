package ru.abradox.demospring.controller.editors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.entity.Country;
import ru.abradox.demospring.model.repository.CountryRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/country")
public class CountryController {
    private final CountryRepository objectRepository;

    public CountryController(CountryRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @GetMapping("/editor")
    public String showEditorForm(@RequestParam(name = "country", required = false) Long id, Model model) {
        List<Country> countries = objectRepository.findAll();
        model.addAttribute("countries", countries);
        Country country = (id == null) ? new Country() : objectRepository.findById(id).orElse(new Country());
        model.addAttribute("country", country);
        return "editors/country-editor";
    }

    @PostMapping("/create")
    public String create(@RequestBody CreateRequest request) {
        String title = request.getTitle();
        log.debug("Получен запрос на создание {}", title);
        if (title.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByTitle(title)) throw new RuntimeException();
        var country = objectRepository.save(new Country(request.getTitle()));
        log.debug("Создан объект {}", country.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("country") Country country, BindingResult result) {
        log.debug("в update поступил {}", country);
        if (result.hasErrors() || country==null || country.getId()==null || !objectRepository.existsById(country.getId())) {
            return "redirect:/country/editor";
        }
        if (country.getTitle().isBlank()) return "redirect:/country/editor?country=" +  country.getId();
        objectRepository.save(country);
        return "redirect:/country/editor?country=" +  country.getId();
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("country") Country country, BindingResult result) {
        log.debug("delete {}", country);
        if (result.hasErrors() || country == null || country.getId() == null) {
            return "redirect:/country/editor";
        }
        if (objectRepository.existsById(country.getId())) objectRepository.deleteById(country.getId());
        return "redirect:/country/editor";
    }

    @Data
    private static class CreateRequest {
        private String title;
    }
}
