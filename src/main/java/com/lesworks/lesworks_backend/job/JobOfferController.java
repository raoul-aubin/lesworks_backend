package com.lesworks.lesworks_backend.job;

import com.lesworks.lesworks_backend.user.User;
import com.lesworks.lesworks_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    /**
     * 1️⃣ Créer une offre d’emploi
     * L’utilisateur connecté est automatiquement le client
     */
    @PostMapping
    public ResponseEntity<JobOffer> createJobOffer(
            @RequestBody JobOffer jobOffer,
            Authentication authentication
    ) {
        // Récupérer l’email de l’utilisateur connecté (JWT)
        String email = authentication.getName();

        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        jobOffer.setClient(client);
        jobOffer.setCreatedAt(LocalDateTime.now());

        JobOffer savedOffer = jobOfferRepository.save(jobOffer);
        return ResponseEntity.ok(savedOffer);
    }

    /**
     * 2️⃣ Lister toutes les offres
     */
    @GetMapping
    public ResponseEntity<List<JobOffer>> getAllJobOffers() {
        return ResponseEntity.ok(jobOfferRepository.findAll());
    }

    /**
     * 3️⃣ Récupérer une offre par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable Long id) {
        return jobOfferRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 4️⃣ Mettre à jour une offre (seulement le client propriétaire)
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(
            @PathVariable Long id,
            @RequestBody JobOffer updatedOffer,
            Authentication authentication
    ) {
        String email = authentication.getName();

        JobOffer existingOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        // Vérifier que l'utilisateur connecté est le propriétaire
        if (!existingOffer.getClient().getEmail().equals(email)) {
            return ResponseEntity.status(403).build();
        }

        // Mise à jour des champs autorisés
        existingOffer.setTitle(updatedOffer.getTitle());
        existingOffer.setLocation(updatedOffer.getLocation());
        existingOffer.setCategory(updatedOffer.getCategory());
        existingOffer.setDescription(updatedOffer.getDescription());
        existingOffer.setDailySalary(updatedOffer.getDailySalary());
        existingOffer.setCurrency(updatedOffer.getCurrency());

        JobOffer saved = jobOfferRepository.save(existingOffer);
        return ResponseEntity.ok(saved);
    }

    /**
     * 5️⃣ Supprimer une offre (seulement le client propriétaire)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        JobOffer existingOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        if (!existingOffer.getClient().getEmail().equals(email)) {
            return ResponseEntity.status(403).build();
        }

        jobOfferRepository.delete(existingOffer);
        return ResponseEntity.noContent().build();
    }

}

