package ru.abradox.demospring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TestController {
    @GetMapping("/indexOld")
    public String getOldIndexHTML() {
        return "/indexOld";
    }

    @GetMapping("/navbar")
    public String getNavBar() {
        return "/components/navigationBar";
    }
}
