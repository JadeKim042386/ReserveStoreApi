package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.exception.MemberException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.repository.MemberRepository;
import org.zerobase.reservestoreapi.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member searchMemberByUsername(String username) {
        return memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_ENTITY));
    }

    @Override
    public boolean isExistsUsernameOrNickname(String username, String nickname) {
        return memberRepository.existsByUsernameOrNickname(username, nickname);
    }

    @Transactional
    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}
