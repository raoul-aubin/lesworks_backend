package com.lesworks.lesworks_backend.application;

import com.lesworks.lesworks_backend.job.JobOffer;
import com.lesworks.lesworks_backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'offre à laquelle le jobist postule
    @ManyToOne(optional = false)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

    // Le jobist qui postule
    @ManyToOne(optional = false)
    @JoinColumn(name = "jobist_id")
    private User jobist;

    // Message de motivation / infos supplémentaires
    @Column(length = 1000)
    private String message;

    // Date de la candidature
    private LocalDateTime appliedAt;

    // Statut de la candidature : PENDING / ACCEPTED / REJECTED
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
}
