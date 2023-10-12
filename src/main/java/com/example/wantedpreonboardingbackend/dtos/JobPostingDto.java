package com.example.wantedpreonboardingbackend.dtos;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class JobPostingDto {
    private final Long jobPostingId;
    private final String companyName;
    private final String nationality;
    private final String region;
    private final String position;
    private final Integer reward;
    private final String skill;

    public static JobPostingDto from(JobPosting jobPosting) {
        Company company = jobPosting.getCompany();
        return new JobPostingDto(
                jobPosting.getId(), company.getName(), company.getNationality(),
                company.getRegion(), jobPosting.getPosition(), jobPosting.getReward(),
                jobPosting.getSkill()
        );
    }

}
