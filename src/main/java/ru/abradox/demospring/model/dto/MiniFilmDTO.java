package ru.abradox.demospring.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String title;
    private String description;
    @Min(0)
    private Integer duration;
    @DecimalMin(value = "0.0")
    private BigDecimal totalBoxOffice;
    @DecimalMin(value = "0.0")
    private BigDecimal budget;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate releaseDate;

    private Long country;
    private Long genre;
    private Long ageLimit;
    private Long quality;

    private List<String> people;
}
