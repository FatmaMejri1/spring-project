package com.smarthub.smart_career_hub_backend.service;

import com.smarthub.smart_career_hub_backend.entity.ChercheurEmploi;
import com.smarthub.smart_career_hub_backend.entity.Recruteur;
import com.smarthub.smart_career_hub_backend.entity.Administrateur;
import com.smarthub.smart_career_hub_backend.entity.Utilisateur;
import com.smarthub.smart_career_hub_backend.enums.Role;
import com.smarthub.smart_career_hub_backend.repository.UtilisateurRepository;
import com.smarthub.smart_career_hub_backend.repository.ChercheurEmploiRepository;
import com.smarthub.smart_career_hub_backend.repository.RecruteurRepository;
import com.smarthub.smart_career_hub_backend.repository.AdministrateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final ChercheurEmploiRepository chercheurEmploiRepository;
    private final RecruteurRepository recruteurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              ChercheurEmploiRepository chercheurEmploiRepository,
                              RecruteurRepository recruteurRepository,
                              AdministrateurRepository administrateurRepository,
                              PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.chercheurEmploiRepository = chercheurEmploiRepository;
        this.recruteurRepository = recruteurRepository;
        this.administrateurRepository = administrateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // CRUD général utilisateurs
    // =========================

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return Optional.ofNullable(utilisateurRepository.findByEmail(email));
    }

    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // =========================
    // Gestion ChercheurEmploi
    // =========================

    public List<ChercheurEmploi> getAllChercheurs() {
        return chercheurEmploiRepository.findAll();
    }

    public Optional<ChercheurEmploi> getChercheurById(Long id) {
        return chercheurEmploiRepository.findById(id);
    }

    public ChercheurEmploi ajouterChercheur(ChercheurEmploi chercheur) {
        chercheur.setRole(Role.ROLE_CANDIDAT);
        chercheur.setMotDePasse(passwordEncoder.encode(chercheur.getMotDePasse()));
        return chercheurEmploiRepository.save(chercheur);
    }

    public void deleteChercheur(Long id) {
        chercheurEmploiRepository.deleteById(id);
    }

    // =========================
    // Gestion Recruteur
    // =========================

    public List<Recruteur> getAllRecruteurs() {
        return recruteurRepository.findAll();
    }

    public Optional<Recruteur> getRecruteurById(Long id) {
        return recruteurRepository.findById(id);
    }

    public Recruteur ajouterRecruteur(Recruteur recruteur) {
        recruteur.setRole(Role.ROLE_RECRUTEUR);
        recruteur.setMotDePasse(passwordEncoder.encode(recruteur.getMotDePasse()));
        return recruteurRepository.save(recruteur);
    }

    public void deleteRecruteur(Long id) {
        recruteurRepository.deleteById(id);
    }

    // =========================
    // Gestion Administrateur
    // =========================

    public List<Administrateur> getAllAdmins() {
        return administrateurRepository.findAll();
    }

    public Optional<Administrateur> getAdminById(Long id) {
        return administrateurRepository.findById(id);
    }

    public Administrateur ajouterAdmin(Administrateur admin) {
        admin.setRole(Role.ROLE_ADMIN);
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
        return administrateurRepository.save(admin);
    }

    public void deleteAdmin(Long id) {
        administrateurRepository.deleteById(id);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        if (utilisateurDetails.getMotDePasse() != null && !utilisateurDetails.getMotDePasse().isEmpty()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurDetails.getMotDePasse()));
        }
        utilisateur.setRole(utilisateurDetails.getRole());

        return utilisateurRepository.save(utilisateur);
    }
}
