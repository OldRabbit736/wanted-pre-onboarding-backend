package com.example.wantedpreonboardingbackend.services;

import com.example.wantedpreonboardingbackend.domains.Apply;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.domains.Member;
import com.example.wantedpreonboardingbackend.dtos.ApplyJobRequest;
import com.example.wantedpreonboardingbackend.exceptionHandlers.CustomException;
import com.example.wantedpreonboardingbackend.repositories.ApplyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
import com.example.wantedpreonboardingbackend.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final JobPostingRepository jobPostingRepository;

    public void applyAJob(ApplyJobRequest request) {
        if (applyRepository.existsByMemberIdAndJobPostingId(request.getMemberId(), request.getJobPostingId())) {
            throw new CustomException(CustomException.ExceptionCode.E1001);
        }
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new CustomException(CustomException.ExceptionCode.E1000));
        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow(() -> new CustomException(CustomException.ExceptionCode.E1000));
        Apply apply = new Apply(jobPosting, member);
        applyRepository.save(apply);
    }
}
