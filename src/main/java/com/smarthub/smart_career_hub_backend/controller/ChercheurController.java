package com.smarthub.smart_career_hub_backend.controller;

import com.smarthub.smart_career_hub_backend.entity.ChercheurEmploi;
import com.smarthub.smart_career_hub_backend.entity.Quiz;
import com.smarthub.smart_career_hub_backend.entity.Coaching;
import com.smarthub.smart_career_hub_backend.entity.Formation;
import com.smarthub.smart_career_hub_backend.service.ChercheurEmploiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chercheur")
public class ChercheurController {

    @Autowired
    private ChercheurEmploiService chercheurEmploiService;

    // Page routes
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "My Dashboard");
        return "candidate/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("pageTitle", "My Profile");
        return "candidate/profile";
    }

    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("pageTitle", "Browse Jobs");
        return "candidate/jobs";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("pageTitle", "My Applications");
        return "candidate/applications";
    }

    @GetMapping("/saved-jobs")
    public String savedJobs(Model model) {
        model.addAttribute("pageTitle", "Saved Jobs");
        return "candidate/saved-jobs";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("pageTitle", "Messages");
        return "candidate/messages";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("pageTitle", "Settings");
        return "candidate/settings";
    }

    // REST API endpoints
    @GetMapping("/api")
    @ResponseBody
    public List<ChercheurEmploi> getAll() {
        return chercheurEmploiService.getAllChercheurs();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ChercheurEmploi> getById(@PathVariable Long id) {
        Optional<ChercheurEmploi> chercheur = chercheurEmploiService.getChercheurById(id);
        return chercheur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<ChercheurEmploi> create(@RequestBody ChercheurEmploi chercheur) {
        try {
            ChercheurEmploi savedChercheur = chercheurEmploiService.ajouterChercheur(chercheur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedChercheur);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ChercheurEmploi> update(@PathVariable Long id, @RequestBody ChercheurEmploi chercheur) {
        try {
            ChercheurEmploi updatedChercheur = chercheurEmploiService.updateChercheur(id, chercheur);
            return ResponseEntity.ok(updatedChercheur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            chercheurEmploiService.deleteChercheur(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints spécifiques pour les fonctionnalités chercheur

    @PostMapping("/api/{chercheurId}/quiz")
    @ResponseBody
    public ResponseEntity<Quiz> ajouterQuiz(@PathVariable Long chercheurId, @RequestBody Quiz quiz) {
        try {
            Quiz savedQuiz = chercheurEmploiService.ajouterQuiz(chercheurId, quiz);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuiz);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/{chercheurId}/quiz")
    @ResponseBody
    public ResponseEntity<List<Quiz>> getQuizByChercheur(@PathVariable Long chercheurId) {
        try {
            List<Quiz> quizList = chercheurEmploiService.getQuizByChercheur(chercheurId);
            return ResponseEntity.ok(quizList);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/{chercheurId}/coaching")
    @ResponseBody
    public ResponseEntity<Coaching> ajouterCoaching(@PathVariable Long chercheurId, @RequestBody Coaching coaching) {
        try {
            Coaching savedCoaching = chercheurEmploiService.ajouterCoaching(chercheurId, coaching);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCoaching);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/{chercheurId}/coaching")
    @ResponseBody
    public ResponseEntity<List<Coaching>> getCoachingsByChercheur(@PathVariable Long chercheurId) {
        try {
            List<Coaching> coachings = chercheurEmploiService.getCoachingsByChercheur(chercheurId);
            return ResponseEntity.ok(coachings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}