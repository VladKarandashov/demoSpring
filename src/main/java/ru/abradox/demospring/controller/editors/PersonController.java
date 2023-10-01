package ru.abradox.demospring.controller.editors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.entity.Person;
import ru.abradox.demospring.model.repository.PersonRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository objectRepository;

    public PersonController(PersonRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @GetMapping("/editor")
    public String showEditorForm(@RequestParam(name = "person", required = false) Long id, Model model, @CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        List<Person> persons = objectRepository.findAll();
        model.addAttribute("persons", persons);
        Person person = (id == null) ? new Person() : objectRepository.findById(id).orElse(new Person());
        model.addAttribute("person", person);
        return "editors/person-editor";
    }

    @PostMapping("/create")
    public String create(@RequestBody CreateRequest request) {
        String fullName = request.getFullName();
        log.debug("Получен запрос на создание {}", fullName);
        if (fullName.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByFullName(fullName)) throw new RuntimeException();
        var person = objectRepository.save(new Person(request.getFullName()));
        log.debug("Создан объект {}", person.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("person") Person person, BindingResult result) {
        log.debug("в update поступил {}", person);
        if (result.hasErrors() || person==null || person.getId()==null || !objectRepository.existsById(person.getId())) {
            return "redirect:/person/editor";
        }
        if (person.getFullName().isBlank()) return "redirect:/person/editor?person=" +  person.getId();
        objectRepository.save(person);
        return "redirect:/person/editor?person=" +  person.getId();
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("person") Person person, BindingResult result) {
        log.debug("delete {}", person);
        if (result.hasErrors() || person == null || person.getId() == null) {
            return "redirect:/person/editor";
        }
        if (objectRepository.existsById(person.getId())) objectRepository.deleteById(person.getId());
        return "redirect:/person/editor";
    }

    @Data
    private static class CreateRequest {
        private String fullName;
    }
}
