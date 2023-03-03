package ru.abradox.demospring.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abradox.demospring.model.dto.CreateRequest;
import ru.abradox.demospring.model.dto.CreateResponse;
import ru.abradox.demospring.model.dto.Item;
import ru.abradox.demospring.model.entity.AgeLimit;
import ru.abradox.demospring.model.repository.AgeLimitRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ageLimit")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AgeLimitRestController {
    private final AgeLimitRepository objectRepository;

    @PostMapping("/miniCreate")
    public CreateResponse miniCreate(@RequestBody CreateRequest request) {
        log.debug(request.toString());
        String title = request.getTitle();
        if (title.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByCategory(title)) throw new RuntimeException();
        var ageLimit = objectRepository.save(new AgeLimit(title));
        var item = new Item(ageLimit.getId(), ageLimit.getCategory());
        List<Item> ageLimits = objectRepository.findAll().stream().map(el -> new Item(el.getId(), el.getCategory())).toList();
        return new CreateResponse(ageLimits, item);
    }
}
