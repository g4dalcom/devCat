package com.project.devcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/main")
    public String main() {
        return "index.html";
    }
}
