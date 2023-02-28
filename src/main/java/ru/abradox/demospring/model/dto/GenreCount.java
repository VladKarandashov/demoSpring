package ru.abradox.demospring.model.dto;

import ru.abradox.demospring.model.entity.Genre;

public interface GenreCount {
    Genre getGenre();
    Long getCount();
}