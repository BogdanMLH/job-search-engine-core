package com.jobapplication.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "candidates")
@Data
@EqualsAndHashCode(callSuper = true)
public class Candidate extends AppUser {

    private String headline;

    private String employmentStatus;

    private Boolean hasExperience;

    private String experienceYears;

    private String lastPosition;
    private String lastCompany;
}