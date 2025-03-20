package org.example.frontendservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index.html"; // Возвращает HTML страницу из ресурсов
    }
}
