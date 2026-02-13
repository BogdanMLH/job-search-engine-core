package com.jobapplication.service;

import com.jobapplication.model.AppUser;
import com.jobapplication.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    public AppUser findById(UUID id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }
}