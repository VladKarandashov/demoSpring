package ru.abradox.demospring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemFilmImpl {
    Long id;
    String title;

    @Override
    public String toString() {
        return "ItemFilmImpl{" +"id=" + id + ", title='" + title + '\'' + '}';
    }
}
