package com.lesworks.lesworks_backend.job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    
    // Chercher les offres qui contiennent un texte dans la localisation (ex: "douala")
    List<JobOffer> findByLocationContainingIgnoreCase(String location);

    // Chercher par catégorie exacte (ex: "Construction", "Agriculture", etc.)
    List<JobOffer> findByCategoryIgnoreCase(String category); 
}
