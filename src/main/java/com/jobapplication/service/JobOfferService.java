package com.jobapplication.service;

import com.jobapplication.model.JobOffer;
import com.jobapplication.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobOfferService {
    private final JobOfferRepository jobOfferRepository;

    public List<JobOffer> findAll() { return jobOfferRepository.findAll(); }
    public JobOffer findById(Long id) { return jobOfferRepository.findById(id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); }

    public JobOffer create(JobOffer jobOffer) { return jobOfferRepository.save(jobOffer); }
    public List<JobOffer> createMany(List<JobOffer> jobOffers) {
        return jobOfferRepository.saveAll(jobOffers);
    }

    public JobOffer update(Long id, JobOffer jobOffer) {
        JobOffer old = findById(id);
        old.setTitle(jobOffer.getTitle()); old.setDescription(jobOffer.getDescription());
        old.setCompany(jobOffer.getCompany()); old.setLocation(jobOffer.getLocation());

        return jobOfferRepository.save(jobOffer);
    }

    public void delete(Long id) { jobOfferRepository.deleteById(id); }
}
