package com.example.wantedpreonboardingbackend.services;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.repositories.CompanyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    public void createJobPosting(CreateJobPostingRequest request) {
        Company company = companyRepository.findById(request.getCompany_id())
                .orElseThrow(() -> new RuntimeException());// TODO: Exception 추가, 처리 로직 추가

        JobPosting jobPosting = request.toJobPosting(company);
        jobPostingRepository.save(jobPosting);
    }
}
