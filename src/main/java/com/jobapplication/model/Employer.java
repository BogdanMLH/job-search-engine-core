package com.jobapplication.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "employers")
@Data
@EqualsAndHashCode(callSuper = true)
public class Employer extends AppUser {

    private String companyName;
    private String companyWebsite;

    private String positionInCompany;
}