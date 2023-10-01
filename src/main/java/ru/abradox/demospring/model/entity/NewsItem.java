package ru.abradox.demospring.model.entity;

import com.apptasticsoftware.rssreader.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;

    @Column(name = "author")
    private String author;

    @Column(name = "pubDate")
    private String pubDate;

    @Column(name = "imageLink")
    private String imageLink;

    public NewsItem(Item item) {
        this.title = item.getTitle().orElse("Срочная новость!");
        this.description = item.getDescription().orElse(null);
        this.link = item.getLink().orElse(null);
        this.author = item.getAuthor().orElse(null);
        this.pubDate = item.getPubDate().orElse(null);
    }
}
