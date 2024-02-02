package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.domain.Store;
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

  @Transactional
  @Override
  public void signUp(SignUpRequest signUpRequest) {
    validationMemberCheck(signUpRequest.getUsername(), signUpRequest.getNickname());
    memberService.saveMember(signUpRequest.toMemberEntity());
  }

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
    // save store
    Store store = storeService.saveStore(signUpRequest.getPartnerInfo().toStoreEntity());
    // save store member
    memberService.saveMember(signUpRequest.toStoreMemberEntity(store));
  }

  /** Check validation for username and nickname */
  private void validationMemberCheck(String username, String nickname) {
    if (memberService.isExistsUsername(username) || memberService.isExistsNickname(nickname)) {
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
