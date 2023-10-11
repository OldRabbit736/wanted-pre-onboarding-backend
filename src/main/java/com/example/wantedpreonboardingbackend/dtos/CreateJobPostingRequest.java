package com.example.wantedpreonboardingbackend.dtos;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CreateJobPostingRequest {
    private Long company_id;
    private String position;
    private Integer reward;
    private String detail;
    private String skill;

    public JobPosting toJobPosting(Company company) {
        return new JobPosting(company, position, reward, detail, skill);
    }

    @Builder
    public CreateJobPostingRequest(Long company_id, String position, Integer reward, String detail, String skill) {
        this.company_id = company_id;
        this.position = position;
        this.reward = reward;
        this.detail = detail;
        this.skill = skill;
    }
}
