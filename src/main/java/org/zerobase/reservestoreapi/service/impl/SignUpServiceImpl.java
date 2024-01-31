package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.request.SignUpRequest;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.SignUpService;
import org.zerobase.reservestoreapi.service.StoreService;
import org.zerobase.reservestoreapi.utils.LocalDateTimeUtils;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final MemberService memberService;
    private final StoreService storeService;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        validationMemberCheck(
                signUpRequest.getUsername(),
                signUpRequest.getNickname()
        );
        memberService.saveMember(signUpRequest.toMemberEntity());
    }

    @Override
    public void partnerSignUp(SignUpRequest signUpRequest) {
        validationStoreCheck(
                signUpRequest.getUsername(),
                signUpRequest.getNickname(),
                signUpRequest.getPartnerInfo().getStoreName()
        );
        //intervalTime must not be greater than lastTime - startTime
        //TODO: exception handle
        if (
                LocalDateTimeUtils.diffMinutes(
                        signUpRequest.getPartnerInfo().getStartTime(),
                        signUpRequest.getPartnerInfo().getLastTime()
                ) < signUpRequest.getPartnerInfo().getIntervalTime()
        ) throw new RuntimeException("intervalTime must not be greater than lastTime - startTime");
        //save store
        Store store = storeService.saveStore(signUpRequest.getPartnerInfo().toStoreEntity());
        //save store member
        memberService.saveMember(signUpRequest.toStoreMemberEntity(store));
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
