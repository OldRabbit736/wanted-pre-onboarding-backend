package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.dtos.GetJobPostingsResponse;
import com.example.wantedpreonboardingbackend.dtos.PatchJobPostingRequest;
import com.example.wantedpreonboardingbackend.services.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/job-postings")
    public void createJobPosting(@RequestBody CreateJobPostingRequest request) {
        jobPostingService.createJobPosting(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/job-postings/{id}")
    public void patchJobPosting(@PathVariable("id") Long jobPostingId, @RequestBody PatchJobPostingRequest request) {
        jobPostingService.patchJobPosting(jobPostingId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/job-postings/{id}")
    public void deleteJobPosting(@PathVariable("id") Long jobPostingId) {
        jobPostingService.deleteJobPosting(jobPostingId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/job-postings", produces = "application/json; charset=utf8")
    public GetJobPostingsResponse getJobPostings(@RequestParam(name = "search", required = false) String search) {
        return jobPostingService.getJobPostings(search);
    }
}
