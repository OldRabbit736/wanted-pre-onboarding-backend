package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.services.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/job-postings")
    public void createJobPosting(@RequestBody CreateJobPostingRequest request) {
        jobPostingService.createJobPosting(request);
    }
}
