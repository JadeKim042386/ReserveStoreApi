package org.zerobase.reservestoreapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.request.PartnerSignUpRequest;
import org.zerobase.reservestoreapi.dto.request.SignUpRequest;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.StoreService;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {
    @InjectMocks private SignUpServiceImpl signUpService;
    @Mock private MemberService memberService;
    @Mock private StoreService storeService;

    @DisplayName("member sign up")
    @Test
    void signUp() {
        // given
        SignUpRequest signUpRequest = createMemberSignUpRequest();
        given(memberService.isExistsUsername(anyString())).willReturn(false);
        given(memberService.isExistsNickname(anyString())).willReturn(false);
        willDoNothing().given(memberService).saveMember(any());
        // when
        assertThatNoException().isThrownBy(() -> signUpService.signUp(signUpRequest));
        // then
    }

    @DisplayName("store sign up")
    @Test
    void partnerSignUp() {
        // given
        SignUpRequest partnerSignUpRequest = createPartnerSignUpRequest();
        Store store = partnerSignUpRequest.getPartnerInfo().toStoreEntity();
        given(memberService.isExistsUsername(anyString())).willReturn(false);
        given(memberService.isExistsNickname(anyString())).willReturn(false);
        given(storeService.isExistsStoreName(anyString())).willReturn(false);
        given(storeService.saveStore(any())).willReturn(store);
        willDoNothing().given(memberService).saveMember(any());
        // when
        assertThatNoException().isThrownBy(() -> signUpService.partnerSignUp(partnerSignUpRequest));
        // then
    }

    private static SignUpRequest createMemberSignUpRequest() {
        return new SignUpRequest(
                "username",
                "pw",
                "nickname",
                "MEMBER",
                "zipcode",
                "street",
                "detail",
                "01011111111",
                null);
    }

    private static SignUpRequest createPartnerSignUpRequest() {
        return new SignUpRequest(
                "username",
                "pw",
                "nickname",
                "MEMBER",
                "zipcode",
                "street",
                "detail",
                "01011111111",
                new PartnerSignUpRequest(
                        "store", LocalTime.of(9, 0), LocalTime.of(18, 0), 30, "BAR"));
    }
}
