package ru.abradox.demospring.controller;

import com.apptasticsoftware.rssreader.RssReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.abradox.demospring.model.entity.NewsItem;
import ru.abradox.demospring.model.entity.RssUrl;
import ru.abradox.demospring.model.repository.NewsRepository;
import ru.abradox.demospring.model.repository.RssUrlRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RssController {

    private final RssUrlRepository rssUrlRepository;
    private final NewsRepository newsRepository;

    @GetMapping("/rss")
    public String getRssHTML(Model model) {

        var news = new ArrayList<NewsItem>();

        var urls = rssUrlRepository.findAll().stream().map(RssUrl::getUrl).toList();
        if (!urls.isEmpty()) {
            urls.forEach(url -> {
                try {
                    var items = new ArrayList<>(new RssReader().read(url).limit(4).map(NewsItem::new).toList());
                    news.addAll(items.stream().toList());
                } catch (IOException ignored) {}
            });
        }
        Collections.shuffle(news);
        var items = newsRepository.findAll();
        news.addAll(items);

        model.addAttribute("news", news);
        return "/rss";
    }

    @GetMapping("/rss/urls")
    public String getRssUrlsHTML(Model model, @CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        var urls = rssUrlRepository.findAll();
        model.addAttribute("urls", urls);
        return "rssUrls";
    }

    @GetMapping("/rss/new")
    public String getRssNewHTML(@CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "redirect:/auth";
        return "rssNew";
    }
}
