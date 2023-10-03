package ru.abradox.demospring.controller;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.abradox.demospring.model.entity.NewsItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@UtilityClass
public class RssUtils {

    public List<NewsItem> getNewsFromRss(String rssLink) {
        var newsList = new ArrayList<NewsItem>();

        try {
            URL url = new URL(rssLink);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            CompletableFuture<List<SyndEntry>> future = CompletableFuture.supplyAsync(feed::getEntries);
            List<SyndEntry> entries;
            try {
                entries = future.get(2, TimeUnit.SECONDS);
            } catch (RuntimeException e) {
                log.error("Слишком долго получаю данные от "+rssLink);
                return Collections.emptyList();
            }
            for (SyndEntry entry : entries) {
                var newsItem = NewsItem.builder()
                        .title(entry.getTitle())
                        .description(entry.getDescription().getValue())
                        .link(entry.getLink())
                        .author(entry.getAuthor())
                        .pubDate(entry.getPublishedDate().toString())
                        .build();
                newsList.add(newsItem);
                if (newsList.size() >= 5) break;
            }
        } catch (Exception e) {
            log.error("Ошибка при парсинге rss по ссылке "+rssLink);
        }

        Collections.shuffle(newsList);

        return newsList;
    }
}
