package com.project.devcat.repository;

import com.project.devcat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByKakaoId(Long kakaoId);

    boolean existsByEmail(String email);
}
