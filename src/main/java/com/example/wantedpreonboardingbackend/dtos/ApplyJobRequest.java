package com.example.wantedpreonboardingbackend.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplyJobRequest {
    private Long jobPostingId;
    private Long memberId;

    public ApplyJobRequest(Long jobPostingId, Long memberId) {
        this.jobPostingId = jobPostingId;
        this.memberId = memberId;
    }
}
