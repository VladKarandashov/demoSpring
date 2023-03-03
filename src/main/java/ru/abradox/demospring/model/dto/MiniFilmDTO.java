package ru.abradox.demospring.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.abradox.demospring.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class MiniFilmDTO {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private BigDecimal totalBoxOffice;
    private BigDecimal budget;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate releaseDate;

    private Country country;
    private Genre genre;
    private AgeLimit ageLimit;
    private Quality quality;

    private List<String> people;
}
