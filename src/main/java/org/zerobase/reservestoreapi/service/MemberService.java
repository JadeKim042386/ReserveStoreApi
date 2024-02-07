package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Member;

public interface MemberService {
    Member searchMemberByUsername(String username);

    /** Check already exists username or nickname */
    boolean isExistsUsernameOrNickname(String username, String nickname);

    void saveMember(Member member);

    boolean isExistsByUsernameAndStoreId(String username, Long storeId);
}
