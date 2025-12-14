package com.lesworks.lesworks_backend.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Toutes les candidatures d'un jobist (par son id)
    List<JobApplication> findByJobistId(Long jobistId);

    // Toutes les candidatures pour une offre (par id d'offre)
    List<JobApplication> findByJobOfferId(Long jobOfferId);
}

