package ru.abradox.demospring.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AuthController {
    @GetMapping("/auth")
    public String getAuthHTML(@CookieValue(value = "JSESSIONID", required = false) String token) {
        if (StringUtils.isBlank(token)) return "/auth";
        return "/logout";
    }
}
