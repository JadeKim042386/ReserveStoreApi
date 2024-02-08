package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Member;

public interface MemberService {
    /** Retrieve member by username */
    Member searchMemberByUsername(String username);

    /** Check already exists username or nickname */
    boolean isExistsUsernameOrNickname(String username, String nickname);

    /** Save member */
    void saveMember(Member member);

    /** Check exists member had username and store id */
    boolean isExistsByUsernameAndStoreId(String username, Long storeId);
}
