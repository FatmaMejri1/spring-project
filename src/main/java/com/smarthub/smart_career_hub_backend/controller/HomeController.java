package com.smarthub.smart_career_hub_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Smart Career Hub");
        model.addAttribute("tagline", "Your Gateway to Career Success");
        return "index";
    }
}
