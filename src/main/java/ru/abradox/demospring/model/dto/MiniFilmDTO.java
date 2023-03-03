package ru.abradox.demospring.model.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString
public class MiniFilmDTO {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private BigDecimal totalBoxOffice;
    private BigDecimal budget;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate releaseDate;

    private Long country;
    private Long genre;
    private Long ageLimit;
    private Long quality;

    private List<String> people;
}
