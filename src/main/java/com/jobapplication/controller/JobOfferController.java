package com.jobapplication.controller;

import com.jobapplication.model.Employer; // Импортируем Employer!
import com.jobapplication.model.JobOffer;
import com.jobapplication.repository.EmployerRepository; // Импортируем репозиторий!
import com.jobapplication.service.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;
    private final EmployerRepository employerRepository;

    @GetMapping
    public List<JobOffer> getAllJobOffers() {
        return jobOfferService.findAll();
    }

    @GetMapping("/{id}")
    public JobOffer getJobOfferById(@PathVariable Long id) {
        return jobOfferService.findById(id);
    }

    @PostMapping
    public JobOffer createOffer(@RequestBody JobOffer offer, @AuthenticationPrincipal Jwt token) {
        UUID userId = UUID.fromString(token.getSubject());
        Employer employer = employerRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Employers can post jobs."));
        if (offer.getCompany() == null || offer.getCompany().isEmpty()) {
            offer.setCompany(employer.getCompanyName());
        }

        return jobOfferService.create(offer);
    }

    @PutMapping("/{id}")
    public JobOffer updateJobOffer(@PathVariable Long id, @RequestBody JobOffer jobOffer) {
        return jobOfferService.update(id, jobOffer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobOffer(@PathVariable Long id) {
        jobOfferService.delete(id);
    }
}