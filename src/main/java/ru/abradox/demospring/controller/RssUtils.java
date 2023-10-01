package ru.abradox.demospring.controller;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.experimental.UtilityClass;
import ru.abradox.demospring.model.entity.NewsItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class RssUtils {

    public List<NewsItem> getNewsFromRss(String rssLink) {
        var newsList = new ArrayList<NewsItem>();

        try {
            URL url = new URL(rssLink);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            for (SyndEntry entry : feed.getEntries()) {
                var newsItem = NewsItem.builder()
                        .title(entry.getTitle())
                        .description(entry.getDescription().getValue())
                        .link(entry.getLink())
                        .author(entry.getAuthor())
                        .pubDate(entry.getPublishedDate().toString())
                        .build();
                newsList.add(newsItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.shuffle(newsList);

        return newsList.stream().limit(5).toList();
    }
}
