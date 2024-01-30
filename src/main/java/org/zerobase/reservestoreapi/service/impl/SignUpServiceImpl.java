package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.request.PartnerSignUpRequest;
import org.zerobase.reservestoreapi.dto.request.SignUpRequest;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.SignUpService;
import org.zerobase.reservestoreapi.service.StoreService;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final MemberService memberService;
    private final StoreService storeService;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        validationMemberCheck(
                signUpRequest.username(),
                signUpRequest.nickname()
        );
        memberService.saveMember(signUpRequest.toMemberEntity());
    }

    @Override
    public void partnerSignUp(PartnerSignUpRequest partnerSignUpRequest) {
        validationStoreCheck(
                partnerSignUpRequest.signUpRequest().username(),
                partnerSignUpRequest.signUpRequest().nickname(),
                partnerSignUpRequest.storeName()
        );
        //save store
        Store store = storeService.saveStore(partnerSignUpRequest.toStoreEntity());
        //save store member
        memberService.saveMember(partnerSignUpRequest.signUpRequest().toStoreMemberEntity(store));
    }

    /**
     * Check validation for username and nickname
     */
    private void validationMemberCheck(String username, String nickname) {
        if (memberService.isExistsUsername(username) || memberService.isExistsNickname(nickname)) {
            //TODO: exception handle
            throw new RuntimeException("already exists nickname or username");
        }
    }

    /**
     * Check validation for store name, username, nickname
     */
    private void validationStoreCheck(String username, String nickname, String storeName) {
        validationMemberCheck(username, nickname);
        if (storeService.isExistsStoreName(storeName)) {
            //TODO: exception handle
            throw new RuntimeException("already exists store name");
        }
    }
}
