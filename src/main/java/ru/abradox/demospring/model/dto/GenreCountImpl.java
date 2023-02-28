package ru.abradox.demospring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.abradox.demospring.model.entity.Genre;

@Data
@AllArgsConstructor
public class GenreCountImpl {
    private Genre genre;
    private Long count;
}
