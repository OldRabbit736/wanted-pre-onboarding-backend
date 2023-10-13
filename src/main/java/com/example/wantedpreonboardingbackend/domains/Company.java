package com.example.wantedpreonboardingbackend.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private String region;

    @OneToMany(mappedBy = "company")
    private List<JobPosting> jobPostings = new ArrayList<>();

    public Company(String name, String nationality, String region) {
        this.name = name;
        this.nationality = nationality;
        this.region = region;
    }
}
