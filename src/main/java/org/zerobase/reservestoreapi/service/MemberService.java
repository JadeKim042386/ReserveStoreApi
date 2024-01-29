package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Member;

public interface MemberService {
    Member searchMemberByUsername(String username);
}
