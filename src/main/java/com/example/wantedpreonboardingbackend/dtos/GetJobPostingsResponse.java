package com.example.wantedpreonboardingbackend.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetJobPostingsResponse {

    private List<JobPostingDto> jobPostings;

    public GetJobPostingsResponse(List<JobPostingDto> jobPostings) {
        this.jobPostings = jobPostings;
    }
}
