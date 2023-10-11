package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.repositories.CompanyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class JobPostingControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JobPostingRepository jobPostingRepository;
    @Autowired
    CompanyRepository companyRepository;

    @DisplayName("채용공고 등록 성공")
    @Test
    void createJobPosting() throws Exception {
        Company company = Company.builder()
                .name("원티드랩")
                .nationality("한국")
                .region("서울")
                .build();
        companyRepository.save(company);

        CreateJobPostingRequest request = CreateJobPostingRequest.builder()
                .company_id(company.getId())
                .position("백엔드 주니어 개발자")
                .reward(1000000)
                .detail("원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..")
                .skill("Python")
                .build();

        mockMvc.perform(post("/job-postings")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

        List<JobPosting> all = jobPostingRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        JobPosting jobPosting = all.get(0);
        assertThat(jobPosting.getPosition()).isEqualTo(request.getPosition());
        assertThat(jobPosting.getReward()).isEqualTo(request.getReward());
        assertThat(jobPosting.getDetail()).isEqualTo(request.getDetail());
        assertThat(jobPosting.getReward()).isEqualTo(request.getReward());
    }
}
