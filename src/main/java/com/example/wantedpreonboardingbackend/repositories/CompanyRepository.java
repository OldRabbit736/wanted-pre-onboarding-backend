package com.example.wantedpreonboardingbackend.repositories;

import com.example.wantedpreonboardingbackend.domains.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
