package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.repository.MemberRepository;
import org.zerobase.reservestoreapi.service.MemberService;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member searchMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(EntityExistsException::new);//TODO: exception handling
    }


    @Override
    public boolean isExistsUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}
