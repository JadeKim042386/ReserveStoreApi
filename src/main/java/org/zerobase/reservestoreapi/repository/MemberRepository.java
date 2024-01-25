package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
