package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    boolean existsByUsernameOrNickname(String username, String nickname);

    /** Check exists store member had specific storeId and username */
    boolean existsByUsernameAndStoreId(String username, Long storeId);
}
