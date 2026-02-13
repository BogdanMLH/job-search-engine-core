package com.jobapplication.controller;

import com.jobapplication.model.Employer;
import com.jobapplication.model.JobOffer;
import com.jobapplication.repository.EmployerRepository;
import com.jobapplication.service.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private EmployerRepository employerRepository;
    private JobOfferService jobOfferService;

    // Можно назвать метод /sync или /profile, так как он и создает, и обновляет
    @PostMapping
    public JobOffer createOffer(@RequestBody JobOffer offer, @AuthenticationPrincipal Jwt token) {
        UUID userId = UUID.fromString(token.getSubject());

        // 1. Используем EmployerRepository!
        // Мы ожидаем, что если токен валиден и роль = EMPLOYER, то запись в таблице employers УЖЕ есть (спасибо триггеру)
        Employer employer = employerRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found or not an employer"));

        // 2. Теперь типы совпадают (JobOffer ждет Employer)
        offer.setEmployer(employer);

        // 3. Логика имени компании
        if (offer.getCompany() == null) {
            // Берем из профиля работодателя
            offer.setCompany(employer.getCompanyName());
        }

        return jobOfferService.create(offer);
    }
}