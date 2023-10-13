package com.example.wantedpreonboardingbackend.repositories;

import com.example.wantedpreonboardingbackend.domains.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByMemberIdAndJobPostingId(Long memberId, Long jobPostingId);
}
