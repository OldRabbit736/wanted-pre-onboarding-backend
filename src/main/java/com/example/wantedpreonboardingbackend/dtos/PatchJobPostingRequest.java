package com.example.wantedpreonboardingbackend.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PatchJobPostingRequest {

    private String position;
    private Integer reward;
    private String detail;
    private String skill;

    public PatchJobPostingRequest(String position, Integer reward, String detail, String skill) {
        this.position = position;
        this.reward = reward;
        this.detail = detail;
        this.skill = skill;
    }
}
