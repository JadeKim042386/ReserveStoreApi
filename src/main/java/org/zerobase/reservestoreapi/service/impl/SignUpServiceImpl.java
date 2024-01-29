package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.PartnerSignUpRequest;
import org.zerobase.reservestoreapi.dto.SignUpRequest;
import org.zerobase.reservestoreapi.repository.MemberRepository;
import org.zerobase.reservestoreapi.repository.StoreRepository;
import org.zerobase.reservestoreapi.service.SignUpService;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        isExistsUsername(signUpRequest.username());
        memberRepository.save(signUpRequest.toMemberEntity());
    }

    @Override
    public void partnerSignUp(PartnerSignUpRequest partnerSignUpRequest) {
        isExistsUsername(partnerSignUpRequest.signUpRequest().username());
        isExistsStoreName(partnerSignUpRequest.storeName());
        //save store
        Store store = storeRepository.save(partnerSignUpRequest.toStoreEntity());
        //save store member
        memberRepository.save(partnerSignUpRequest.signUpRequest().toStoreMemberEntity(store));
    }

    @Override
    public void isExistsNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            //TODO: exception handle
            throw new RuntimeException("already exists nickname");
        }
    }

    @Override
    public void isExistsStoreName(String storeName) {
        if (storeRepository.existsByName(storeName)) {
            //TODO: exception handle
            throw new RuntimeException("already exists store name");
        }
    }

    /**
     * Check already exists username
     */
    private void isExistsUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            //TODO: exception handle
            throw new RuntimeException("already exists username");
        }
    }



}
