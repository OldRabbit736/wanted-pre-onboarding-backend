package com.example.wantedpreonboardingbackend.services;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.domains.Member;
import com.example.wantedpreonboardingbackend.dtos.ApplyJobRequest;
import com.example.wantedpreonboardingbackend.exceptionHandlers.CustomException;
import com.example.wantedpreonboardingbackend.repositories.ApplyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
import com.example.wantedpreonboardingbackend.repositories.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplyServiceUnitTest {

    @Mock
    ApplyRepository applyRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    JobPostingRepository jobPostingRepository;
    @InjectMocks
    ApplyService applyService;

    @DisplayName("채용공고 지원 성공")
    @Test
    void applyAJob() {
        // given
        when(applyRepository.existsByMemberIdAndJobPostingId(anyLong(), anyLong()))
                .thenReturn(false);
        Member member = new Member("사용자1");
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        Company company = new Company("원티드랩", "한국", "서울");
        JobPosting jobPosting = new JobPosting(company, "Java 백엔드 개발자", 10000, "백엔드 주니어 개발자", "Java");
        when(jobPostingRepository.findById(anyLong()))
                .thenReturn(Optional.of(jobPosting));

        // when
        ApplyJobRequest request = new ApplyJobRequest(1L, 2L);
        applyService.applyAJob(request);

        // then
        verify(applyRepository).existsByMemberIdAndJobPostingId(request.getMemberId(), request.getJobPostingId());
        verify(memberRepository).findById(request.getMemberId());
        verify(jobPostingRepository).findById(request.getJobPostingId());
        verify(applyRepository).save(argThat(
                apply -> apply.getMember().equals(member) && apply.getJobPosting().equals(jobPosting)));
    }

    @DisplayName("채용공고 지원 실패 - 이미 지원한 채용 공고")
    @Test
    void applyAJobFail() {
        // given
        when(applyRepository.existsByMemberIdAndJobPostingId(anyLong(), anyLong()))
                .thenReturn(true);

        // when
        ApplyJobRequest request = new ApplyJobRequest(1L, 2L);
        assertThatThrownBy(() -> applyService.applyAJob(request))
                .isInstanceOf(CustomException.class)
                .hasMessage("이미 해당 공고에 지원했습니다. 중복해서 지원할 수 없습니다.");

        // then
        verify(applyRepository).existsByMemberIdAndJobPostingId(request.getMemberId(), request.getJobPostingId());
    }

}
