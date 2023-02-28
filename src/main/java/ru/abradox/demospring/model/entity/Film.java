package ru.abradox.demospring.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer duration;
    @Column(name = "total_box_office")
    private BigDecimal totalBoxOffice;
    private BigDecimal budget;
    @Column(name = "release_date")
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate releaseDate;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Country country;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Genre genre;
    @ManyToOne(cascade = {CascadeType.ALL})
    private AgeLimit ageLimit;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Quality quality;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<Person> people;

    public Film(String title, String description, Integer duration, BigDecimal totalBoxOffice, BigDecimal budget, LocalDate releaseDate, Quality quality) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.totalBoxOffice = totalBoxOffice;
        this.budget = budget;
        this.releaseDate = releaseDate;
        this.quality = quality;
    }
}
