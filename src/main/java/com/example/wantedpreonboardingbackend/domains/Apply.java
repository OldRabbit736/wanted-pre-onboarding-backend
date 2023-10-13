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
public class Apply {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Apply(JobPosting jobPosting, Member member) {
        setJobPosting(jobPosting);
        setMember(member);
    }

    private void setJobPosting(JobPosting jobPosting) {
        if (this.jobPosting != null) {
            jobPosting.getApplies().remove(this);
        }
        this.jobPosting = jobPosting;
        jobPosting.getApplies().add(this);
    }

    private void setMember(Member member) {
        if (this.member != null) {
            member.getApplies().remove(this);
        }
        this.member = member;
        member.getApplies().add(this);
    }
}
