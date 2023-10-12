package com.example.wantedpreonboardingbackend.controllers;

import com.example.wantedpreonboardingbackend.domains.Company;
import com.example.wantedpreonboardingbackend.domains.JobPosting;
import com.example.wantedpreonboardingbackend.dtos.CreateJobPostingRequest;
import com.example.wantedpreonboardingbackend.dtos.PatchJobPostingRequest;
import com.example.wantedpreonboardingbackend.repositories.CompanyRepository;
import com.example.wantedpreonboardingbackend.repositories.JobPostingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class JobPostingControllerIntegrationTest {

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
    void createJobPostingSuccess() throws Exception {
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

    @DisplayName("채용공고 등록 실패 - 존재하지 않는 회사 아이디로 요청")
    @Test
    void createJobPostingFailed() throws Exception {
        Company company = createCompany();
        Long companyId = company.getId() + 1;
        CreateJobPostingRequest request = getCreateJobPostingRequest(companyId);

        mockMvc.perform(post("/job-postings")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("E1000"))
                .andExpect(jsonPath("$.errorMessage").value("존재하지 않는 리소스입니다."));

        List<JobPosting> all = jobPostingRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @DisplayName("원하는 채용공고 필드 수정 성공")
    @ParameterizedTest
    @MethodSource("patchJobPostingEndpointArgumentsProvider")
    @DirtiesContext
    void patchJobPostingSuccess(TestArg originalPost, TestArg patchPost, TestArg expectedPost) throws Exception {
        Company company = createCompany();
        JobPosting jobPosting = createJobPosting(company, originalPost.position, originalPost.reward, originalPost.detail, originalPost.skill);

        PatchJobPostingRequest request = new PatchJobPostingRequest(patchPost.position, patchPost.reward, patchPost.detail, patchPost.skill);

        mockMvc.perform(patch("/job-postings/{id}", jobPosting.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        List<JobPosting> all = jobPostingRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        JobPosting patchedJobPosting = all.get(0);
        assertThat(patchedJobPosting.getPosition()).isEqualTo(expectedPost.position);
        assertThat(patchedJobPosting.getReward()).isEqualTo(expectedPost.reward);
        assertThat(patchedJobPosting.getDetail()).isEqualTo(expectedPost.detail);
        assertThat(patchedJobPosting.getReward()).isEqualTo(expectedPost.reward);
    }

    @DisplayName("원하는 채용공고 필드 수정 실패 - 존재하지 않는 채용공고 아이디로 요청")
    @Test
    void patchJobPostingFailed() throws Exception {
        Company company = createCompany();
        JobPosting jobPosting = createJobPosting(company, "포지션1", 10000, "설명1", "기술1");

        PatchJobPostingRequest request = new PatchJobPostingRequest("포지션2", 20000, "설명2", "기술2");

        Long jobPostingId = jobPosting.getId() + 1;
        mockMvc.perform(patch("/job-postings/{id}", jobPostingId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("E1000"))
                .andExpect(jsonPath("$.errorMessage").value("존재하지 않는 리소스입니다."));

        List<JobPosting> all = jobPostingRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        JobPosting originalPosting = all.get(0);
        assertThat(originalPosting.getPosition()).isEqualTo(jobPosting.getPosition());
        assertThat(originalPosting.getReward()).isEqualTo(jobPosting.getReward());
        assertThat(originalPosting.getDetail()).isEqualTo(jobPosting.getDetail());
        assertThat(originalPosting.getReward()).isEqualTo(jobPosting.getReward());
    }

    @RequiredArgsConstructor
    static public class TestArg {
        public final String position;
        public final Integer reward;
        public final String detail;
        public final String skill;
    }

    static Stream<Arguments> patchJobPostingEndpointArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new TestArg("백엔드 주니어 개발자", 1000000, "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python"),
                        new TestArg("백엔드 신입 개발자", 2000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java"),
                        new TestArg("백엔드 신입 개발자", 2000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java")
                ),
                // position null
                Arguments.of(
                        new TestArg("백엔드 주니어 개발자", 1000000, "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python"),
                        new TestArg(null, 2000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java"),
                        new TestArg("백엔드 주니어 개발자", 2000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java")
                ),
                // reward null
                Arguments.of(
                        new TestArg("백엔드 주니어 개발자", 1000000, "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python"),
                        new TestArg("백엔드 신입 개발자", null, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java"),
                        new TestArg("백엔드 신입 개발자", 1000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Java")
                ),
                // detail null
                Arguments.of(
                        new TestArg("백엔드 주니어 개발자", 1000000, "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python"),
                        new TestArg("백엔드 신입 개발자", 2000000, null, "Java"),
                        new TestArg("백엔드 신입 개발자", 2000000, "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Java")
                ),
                // skill null
                Arguments.of(
                        new TestArg("백엔드 주니어 개발자", 1000000, "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python"),
                        new TestArg("백엔드 신입 개발자", 2000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", null),
                        new TestArg("백엔드 신입 개발자", 2000000, "원티드랩에서 백엔드 신입 개발자를 '적극' 채용합니다. 자격요건은..", "Python")
                )
        );
    }

    private Company createCompany() {
        Company company = new Company("원티드랩", "한국", "서울");
        return companyRepository.save(company);
    }

    private JobPosting createJobPosting(Company company, String position, Integer reward, String detail, String skill) {
        JobPosting jobPosting = new JobPosting(company, position, reward, detail, skill);
        return jobPostingRepository.save(jobPosting);
    }

    private CreateJobPostingRequest getCreateJobPostingRequest(Long companyId) {
        return new CreateJobPostingRequest(companyId, "백엔드 주니어 개발자", 1000000,
                "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격 요건은..", "Python");
    }
}
