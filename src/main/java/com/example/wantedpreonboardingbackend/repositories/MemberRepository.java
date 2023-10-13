package com.example.wantedpreonboardingbackend.repositories;

import com.example.wantedpreonboardingbackend.domains.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
