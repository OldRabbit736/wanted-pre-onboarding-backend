package com.example.wantedpreonboardingbackend.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class JobPosting {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "jobPosting")
    private List<Apply> applies = new ArrayList<>();

    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private Integer reward;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
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
