package com.example.wantedpreonboardingbackend.repositories;

import com.example.wantedpreonboardingbackend.domains.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}
