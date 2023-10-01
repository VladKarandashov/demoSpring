package ru.abradox.demospring.controller.editors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.abradox.demospring.model.entity.Quality;
import ru.abradox.demospring.model.repository.QualityRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/quality")
public class QualityController {
    private final QualityRepository objectRepository;

    public QualityController(QualityRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @GetMapping("/editor")
    public String showEditorForm(@RequestParam(name = "quality", required = false) Long id, Model model, @CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        List<Quality> qualities = objectRepository.findAll();
        model.addAttribute("qualities", qualities);
        Quality quality = (id == null) ? new Quality() : objectRepository.findById(id).orElse(new Quality());
        model.addAttribute("quality", quality);
        return "editors/quality-editor";
    }

    @PostMapping("/create")
    public String create(@RequestBody CreateRequest request) {
        String type = request.getType();
        log.debug("Получен запрос на создание {}", type);
        if (type.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByType(type)) throw new RuntimeException();
        var quality = objectRepository.save(new Quality(request.getType()));
        log.debug("Создан объект {}", quality.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("quality") Quality quality, BindingResult result) {
        log.debug("в update поступил {}", quality);
        if (result.hasErrors() || quality==null || quality.getId()==null || !objectRepository.existsById(quality.getId())) {
            return "redirect:/quality/editor";
        }
        if (quality.getType().isBlank()) return "redirect:/quality/editor?quality=" +  quality.getId();
        objectRepository.save(quality);
        return "redirect:/quality/editor?quality=" +  quality.getId();
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("quality") Quality quality, BindingResult result) {
        log.debug("delete {}", quality);
        if (result.hasErrors() || quality == null || quality.getId() == null) {
            return "redirect:/quality/editor";
        }
        if (objectRepository.existsById(quality.getId())) objectRepository.deleteById(quality.getId());
        return "redirect:/quality/editor";
    }

    @Data
    private static class CreateRequest {
        private String type;
    }
}
