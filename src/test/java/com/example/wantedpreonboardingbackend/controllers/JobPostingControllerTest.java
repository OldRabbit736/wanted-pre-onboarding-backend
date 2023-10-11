package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.dtos.PatchJobPostingRequest;
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
    void createJobPostingEndpoint() throws Exception {
        Company company = createCompany();
        CreateJobPostingRequest request = getCreateJobPostingRequest(company.getId());

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

    @DisplayName("채용공고 수정 성공")
    @Test
    void patchJobPostingEndpoint() throws Exception {
        Company company = createCompany();
        JobPosting jobPosting = createJobPosting(company);

        PatchJobPostingRequest request = new PatchJobPostingRequest("백엔드 신입 개발자", 2000000,
                "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java");

        mockMvc.perform(patch("/job-postings/{id}", jobPosting.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        List<JobPosting> all = jobPostingRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        JobPosting patchedJobPosting = all.get(0);
        assertThat(patchedJobPosting.getPosition()).isEqualTo(request.getPosition());
        assertThat(patchedJobPosting.getReward()).isEqualTo(request.getReward());
        assertThat(patchedJobPosting.getDetail()).isEqualTo(request.getDetail());
        assertThat(patchedJobPosting.getReward()).isEqualTo(request.getReward());
    }

    private Company createCompany() {
        Company company = new Company("원티드랩", "한국", "서울");
        return companyRepository.save(company);
    }

    private JobPosting createJobPosting(Company company) {
        JobPosting jobPosting = new JobPosting(company, "백엔드 주니어 개발자", 1000000,
                "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python");
        return jobPostingRepository.save(jobPosting);
    }

    private CreateJobPostingRequest getCreateJobPostingRequest(Long companyId) {
        return new CreateJobPostingRequest(companyId, "백엔드 주니어 개발자", 1000000,
                "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python");
    }
}
