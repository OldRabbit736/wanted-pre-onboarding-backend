package com.example.wantedpreonboardingbackend.services;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.dtos.PatchJobPostingRequest;
import com.example.wantedpreonboardingbackend.exceptionHandlers.CustomException;
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
                .orElseThrow(() -> new CustomException(CustomException.ExceptionCode.E1000));

        JobPosting jobPosting = request.toJobPosting(company);
        jobPostingRepository.save(jobPosting);
    }

    public void patchJobPosting(Long jobPostingId, PatchJobPostingRequest request) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CustomException(CustomException.ExceptionCode.E1000));

        if (request.getPosition() != null) {
            jobPosting.changePosition(request.getPosition());
        }
        if (request.getReward() != null) {
            jobPosting.changeReward(request.getReward());
        }
        if (request.getDetail() != null) {
            jobPosting.changeDetail(request.getDetail());
        }
        if (request.getSkill() != null) {
            jobPosting.changeSkill(request.getSkill());
        }
    }

    public void deleteJobPosting(Long jobPostingId) {
        jobPostingRepository.deleteById(jobPostingId);
    }
}
