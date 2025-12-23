package com.smarthub.smart_career_hub_backend.controller;

import com.smarthub.smart_career_hub_backend.dto.JwtResponse;
import com.smarthub.smart_career_hub_backend.dto.LoginRequest;
import com.smarthub.smart_career_hub_backend.entity.ChercheurEmploi;
import com.smarthub.smart_career_hub_backend.entity.Recruteur;
import com.smarthub.smart_career_hub_backend.entity.Utilisateur;
import com.smarthub.smart_career_hub_backend.enums.Role;
import com.smarthub.smart_career_hub_backend.security.JwtTokenUtil;
import com.smarthub.smart_career_hub_backend.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenUtil.generateToken(userDetails);

        Utilisateur user = utilisateurService.getUtilisateurByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getEmail(), user.getRole().name()));
    }

    @PostMapping("/register/chercheur")
    public ResponseEntity<?> registerChercheur(@RequestBody ChercheurEmploi chercheur) {
        if (utilisateurService.getUtilisateurByEmail(chercheur.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        chercheur.setRole(Role.ROLE_CANDIDAT);
        return ResponseEntity.ok(utilisateurService.ajouterChercheur(chercheur));
    }

    @PostMapping("/register/recruteur")
    public ResponseEntity<?> registerRecruteur(@RequestBody Recruteur recruteur) {
        if (utilisateurService.getUtilisateurByEmail(recruteur.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        recruteur.setRole(Role.ROLE_RECRUTEUR);
        return ResponseEntity.ok(utilisateurService.ajouterRecruteur(recruteur));
    }
}
