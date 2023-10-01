package ru.abradox.demospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WidgetsController {

    @GetMapping("/services")
    public String getWidgets() {
        return "services";
    }
}
