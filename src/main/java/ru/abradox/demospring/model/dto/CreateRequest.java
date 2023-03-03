package ru.abradox.demospring.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateRequest {
    private String title;
}