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
import ru.abradox.demospring.model.entity.Quality;
import ru.abradox.demospring.model.repository.QualityRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quality")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class QualityRestController {
    private final QualityRepository objectRepository;

    @PostMapping("/miniCreate")
    public CreateResponse miniCreate(@RequestBody CreateRequest request) {
        log.debug(request.toString());
        String title = request.getTitle();
        if (title.isBlank()) throw new RuntimeException();
        if (objectRepository.existsByType(title)) throw new RuntimeException();
        var quality = objectRepository.save(new Quality(title));
        var item = new Item(quality.getId(), quality.getType());
        List<Item> qualities = objectRepository.findAll().stream().map(el -> new Item(el.getId(), el.getType())).toList();
        return new CreateResponse(qualities, item);
    }
}
