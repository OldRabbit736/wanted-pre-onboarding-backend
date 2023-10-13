package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.dtos.ApplyJobRequest;
import com.example.wantedpreonboardingbackend.services.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/applies")
    public void applyJob(@RequestBody ApplyJobRequest request) {
        applyService.applyAJob(request);
    }

}
