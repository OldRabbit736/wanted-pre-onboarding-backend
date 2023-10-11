package com.example.wantedpreonboardingbackend.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class JobPosting {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String position;
    private Integer reward;
    private String detail;
    private String skill;

    public JobPosting(Company company, String position, Integer reward, String detail, String skill) {
        setCompany(company);
        this.position = position;
        this.reward = reward;
        this.detail = detail;
        this.skill = skill;
    }

    public void setCompany(Company company) {
        if (this.company != null) {
            this.company.getJobPostings().remove(this);
        }
        this.company = company;
        company.getJobPostings().add(this);
    }

    public void changePosition(String position) {
        this.position = position;
    }

    public void changeReward(Integer reward) {
        this.reward = reward;
    }

    public void changeDetail(String detail) {
        this.detail = detail;
    }

    public void changeSkill(String skill) {
        this.skill = skill;
    }
}
