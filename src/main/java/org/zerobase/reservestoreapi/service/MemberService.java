package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Member;

public interface MemberService {
    Member searchMemberByUsername(String username);

    /**
     * Check already exists username
     */
    boolean isExistsUsername(String username);

    /**
     * Check already exists nickname
     */
    boolean isExistsNickname(String username);

    void saveMember(Member member);
}
