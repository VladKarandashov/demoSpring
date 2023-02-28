package ru.abradox.demospring.controller.editors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.entity.AgeLimit;
import ru.abradox.demospring.model.repository.AgeLimitRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/age_limit")
public class AgeLimitController {
    private final AgeLimitRepository objectRepository;

    public AgeLimitController(AgeLimitRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @GetMapping("/editor")
    public String showEditorForm(@RequestParam(name = "age_limit", required = false) Long id, Model model) {
        List<AgeLimit> age_limits = objectRepository.findAll();
        model.addAttribute("age_limits", age_limits);
        AgeLimit age_limit = (id == null) ? new AgeLimit() : objectRepository.findById(id).orElse(new AgeLimit());
        model.addAttribute("age_limit", age_limit);
        return "editors/age_limit-editor";
    }

    @PostMapping("/create")
    public String create(@RequestBody CreateRequest request) {
        String category = request.getCategory();
        log.debug("Получен запрос на создание {}", category);
        if (category.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByCategory(category)) throw new RuntimeException();
        var age_limit = objectRepository.save(new AgeLimit(request.getCategory()));
        log.debug("Создан объект {}", age_limit.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("age_limit") AgeLimit age_limit, BindingResult result) {
        log.debug("в update поступил {}", age_limit);
        if (result.hasErrors() || age_limit==null || age_limit.getId()==null || !objectRepository.existsById(age_limit.getId())) {
            return "redirect:/age_limit/editor";
        }
        if (age_limit.getCategory().isBlank()) return "redirect:/age_limit/editor?age_limit=" +  age_limit.getId();
        objectRepository.save(age_limit);
        return "redirect:/age_limit/editor?age_limit=" +  age_limit.getId();
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("age_limit") AgeLimit age_limit, BindingResult result) {
        log.debug("delete {}", age_limit);
        if (result.hasErrors() || age_limit == null || age_limit.getId() == null) {
            return "redirect:/age_limit/editor";
        }
        if (objectRepository.existsById(age_limit.getId())) objectRepository.deleteById(age_limit.getId());
        return "redirect:/age_limit/editor";
    }

    @Data
    private static class CreateRequest {
        private String category;
    }
}
