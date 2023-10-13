package com.example.wantedpreonboardingbackend.services;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.exceptionHandlers.CustomException;
import com.example.wantedpreonboardingbackend.repositories.CompanyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
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
class JobPostingServiceUnitTest {

    @Mock
    JobPostingRepository jobPostingRepository;
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    JobPostingService jobPostingService;

    @DisplayName("채용공고 등록 성공")
    @Test
    void createJobPosting() {
        // given
        Company company = new Company("원티드랩", "한국", "서울");
        when(companyRepository.findById(anyLong()))
                .thenReturn(Optional.of(company));

        // when
        CreateJobPostingRequest request = new CreateJobPostingRequest(1L, "백엔드", 10000, "주니어 백엔드", "Java");
        jobPostingService.createJobPosting(request);

        // then
        verify(companyRepository).findById(request.getCompany_id());
        JobPosting expectedJobPosting = request.toJobPosting(company);
        verify(jobPostingRepository).save(argThat(jobPosting -> jobPosting.equals(expectedJobPosting)));
    }

    @DisplayName("채용공고 등록 실패 - 존재하지 않는 회사")
    @Test
    void createJobPostingFail() {
        // given
        when(companyRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // when
        CreateJobPostingRequest request = new CreateJobPostingRequest(1L, "백엔드", 10000, "주니어 백엔드", "Java");
        assertThatThrownBy(() -> jobPostingService.createJobPosting(request))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 리소스입니다.");

        // then
        verify(companyRepository).findById(request.getCompany_id());
    }
}
