package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.domains.Apply;
import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.domains.Member;
import com.example.wantedpreonboardingbackend.dtos.ApplyJobRequest;
import com.example.wantedpreonboardingbackend.repositories.ApplyRepository;
import com.example.wantedpreonboardingbackend.repositories.CompanyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
import com.example.wantedpreonboardingbackend.repositories.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.wantedpreonboardingbackend.utilities.TestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ApplyControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    JobPostingRepository jobPostingRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ApplyRepository applyRepository;

    @DisplayName("채용공고 지원 성공")
    @Test
    void applyJobPostingSuccess() throws Exception {
        Company company = createCompany(companyRepository, "원티드랩", "한국", "서울");
        JobPosting jobPosting = createJobPosting(jobPostingRepository, company, "포지션1", 10000, "설명1", "기술1");
        Member member = getMember(memberRepository, "멤버1");

        ApplyJobRequest applyJobRequest = new ApplyJobRequest(jobPosting.getId(), member.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/applies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applyJobRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        List<Apply> all = applyRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        Apply apply = all.get(0);
        assertThat(apply.getJobPosting().getId()).isEqualTo(jobPosting.getId());
        assertThat(apply.getMember().getId()).isEqualTo(member.getId());
    }

    @DisplayName("채용공고 지원 실패 - 중복된 채용공고 지원")
    @Test
    void applyJobPostingFail() throws Exception {
        Company company = createCompany(companyRepository, "원티드랩", "한국", "서울");
        JobPosting jobPosting = createJobPosting(jobPostingRepository, company, "포지션1", 10000, "설명1", "기술1");
        Member member = getMember(memberRepository, "멤버1");

        Apply existingApply = new Apply(jobPosting, member);
        applyRepository.save(existingApply);

        ApplyJobRequest applyJobRequest = new ApplyJobRequest(jobPosting.getId(), member.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/applies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applyJobRequest))
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("E1001"))
                .andExpect(jsonPath("$.errorMessage").value("이미 해당 공고에 지원했습니다. 중복해서 지원할 수 없습니다."));
    }

}
