package com.example.wantedpreonboardingbackend.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
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

    private String name;
    private String Nationality;
    private String Region;

    @OneToMany
    private List<JobPosting> jobPostings = new ArrayList<>();

    @Builder
    public Company(String name, String nationality, String region) {
        this.name = name;
        Nationality = nationality;
        Region = region;
    }
}
