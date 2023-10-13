package com.example.wantedpreonboardingbackend.utilities;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.repositories.CompanyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;

public class TestUtils {
    public static Company createCompany(CompanyRepository companyRepository, String name, String nationality, String region) {
        Company company = new Company(name, nationality, region);
        return companyRepository.save(company);
    }

    public static JobPosting createJobPosting(JobPostingRepository jobPostingRepository, Company company, String position, Integer reward, String detail, String skill) {
        JobPosting jobPosting = new JobPosting(company, position, reward, detail, skill);
        return jobPostingRepository.save(jobPosting);
    }

    public static CreateJobPostingRequest getCreateJobPostingRequest(Long companyId) {
        return new CreateJobPostingRequest(companyId, "백엔드 주니어 개발자", 1000000,
                "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python");
    }
}
