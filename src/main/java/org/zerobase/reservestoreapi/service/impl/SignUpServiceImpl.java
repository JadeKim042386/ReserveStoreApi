package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.dto.constants.CacheKey;
import org.zerobase.reservestoreapi.dto.request.SignUpRequest;
import org.zerobase.reservestoreapi.exception.SignUpException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.SignUpService;
import org.zerobase.reservestoreapi.service.StoreService;
import org.zerobase.reservestoreapi.utils.LocalDateTimeUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUpServiceImpl implements SignUpService {
    private final MemberService memberService;
    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signUp(SignUpRequest signUpRequest) {
        validationMemberCheck(signUpRequest.getUsername(), signUpRequest.getNickname());
        memberService.saveMember(signUpRequest.toMemberEntity(passwordEncoder));
    }

    @CacheEvict(value = CacheKey.KEY_STORE, allEntries = true)
    @Transactional
    @Override
    public void partnerSignUp(SignUpRequest signUpRequest) {
        validationStoreCheck(
                signUpRequest.getUsername(),
                signUpRequest.getNickname(),
                signUpRequest.getPartnerInfo().getStoreName());
        // intervalTime must not be greater than lastTime - startTime
        if (LocalDateTimeUtils.diffMinutes(
                        signUpRequest.getPartnerInfo().getStartTime(),
                        signUpRequest.getPartnerInfo().getLastTime())
                < signUpRequest.getPartnerInfo().getIntervalTime())
            throw new SignUpException(ErrorCode.INVALID_INTERVAL_TIME);
        // save store member
        memberService.saveMember(signUpRequest.toStoreMemberEntity(passwordEncoder));
    }

    /** Check validation for username and nickname */
    private void validationMemberCheck(String username, String nickname) {
        if (memberService.isExistsUsernameOrNickname(username, nickname)) {
            throw new SignUpException(ErrorCode.ALREADY_EXISTS_USERNAME_OR_NICKNAME);
        }
    }

    /** Check validation for store name, username, nickname */
    private void validationStoreCheck(String username, String nickname, String storeName) {
        validationMemberCheck(username, nickname);
        if (storeService.isExistsStoreName(storeName)) {
            throw new SignUpException(ErrorCode.ALREADY_EXISTS_STORE_NAME);
        }
    }
}
