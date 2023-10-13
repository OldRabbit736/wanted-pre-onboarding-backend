package com.example.wantedpreonboardingbackend.repositories;

import com.example.wantedpreonboardingbackend.domains.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    @Query("select p from JobPosting p join fetch p.company c " +
            "where concat(c.name, ' ', c.nationality, ' ', c.region, ' ', p.position, ' ', p.skill) like %:search%")
    List<JobPosting> findWithCompanyContaining(String search);

    @Query("select p from JobPosting p join fetch p.company")
    List<JobPosting> findWithCompany();
}
