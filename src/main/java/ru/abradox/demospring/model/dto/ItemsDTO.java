package ru.abradox.demospring.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.abradox.demospring.model.entity.AgeLimit;
import ru.abradox.demospring.model.entity.Country;
import ru.abradox.demospring.model.entity.Genre;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class ItemsDTO {
    private List<Genre> genreList;
    private List<Country> countryList;
    private List<AgeLimit> ageLimitList;
}
