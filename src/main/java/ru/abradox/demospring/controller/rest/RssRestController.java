package ru.abradox.demospring.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abradox.demospring.model.dto.NewsRequest;
import ru.abradox.demospring.model.dto.RssLinkRequest;
import ru.abradox.demospring.model.entity.NewsItem;
import ru.abradox.demospring.model.entity.RssUrl;
import ru.abradox.demospring.model.repository.NewsRepository;
import ru.abradox.demospring.model.repository.RssUrlRepository;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rss")
public class RssRestController {

    private final RssUrlRepository rssUrlRepository;
    private final NewsRepository newsRepository;

    @PostMapping("/urls")
    public ResponseEntity<Void> createNewRssLink(@RequestBody RssLinkRequest request) {
        var url = request.getUrl();
        rssUrlRepository.save(new RssUrl(url));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/urls/{id}")
    public ResponseEntity<Void> deleteRssLink(@PathVariable("id") Long id) {
        if (rssUrlRepository.existsById(id)) rssUrlRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createNewNews(@RequestBody NewsRequest request) {
        var newsItem = NewsItem.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .link(request.getLink())
                .author(request.getAuthor())
                .pubDate(LocalDate.now().toString())
                .imageLink(request.getImageLink())
                .build();
        newsRepository.save(newsItem);
        return ResponseEntity.ok().build();
    }
}
