package com.smarthub.smart_career_hub_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthPageController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("pageTitle", "Sign In");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("pageTitle", "Create Account");
        return "auth/register";
    }

    @GetMapping("/register-choice")
    public String registerChoice(Model model) {
        model.addAttribute("pageTitle", "Choose Your Path");
        return "auth/register-choice";
    }

    @GetMapping("/register/chercheur")
    public String registerChercheur(Model model) {
        model.addAttribute("pageTitle", "Job Seeker Registration");
        model.addAttribute("userType", "Job Seeker");
        return "auth/register-chercheur";
    }

    @GetMapping("/register/recruteur")
    public String registerRecruteur(Model model) {
        model.addAttribute("pageTitle", "Recruiter Registration");
        model.addAttribute("userType", "Recruiter");
        return "auth/register-recruteur";
    }
}
