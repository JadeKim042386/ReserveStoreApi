package org.zerobase.reservestoreapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.PartnerSignUpRequest;
import org.zerobase.reservestoreapi.dto.SignUpRequest;
import org.zerobase.reservestoreapi.repository.MemberRepository;
import org.zerobase.reservestoreapi.repository.StoreRepository;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {
    @InjectMocks private SignUpServiceImpl signUpService;
    @Mock private MemberRepository memberRepository;
    @Mock private StoreRepository storeRepository;

    @DisplayName("member sign up")
    @Test
    void signUp() {
        //given
        SignUpRequest signUpRequest = createSignUpRequest();
        given(memberRepository.save(any()))
                .willReturn(signUpRequest.toMemberEntity());
        //when
        assertThatNoException()
                        .isThrownBy(() -> signUpService.signUp(signUpRequest));
        //then
    }

    @DisplayName("store sign up")
    @Test
    void partnerSignUp() {
        //given
        PartnerSignUpRequest partnerSignUpRequest = new PartnerSignUpRequest(
                createSignUpRequest(),
                "store",
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                30,
                StoreType.BAR
        );
        Store store = partnerSignUpRequest.toStoreEntity();
        given(storeRepository.save(any()))
                .willReturn(store);
        given(memberRepository.save(any()))
                .willReturn(partnerSignUpRequest.signUpRequest().toStoreMemberEntity(store));
        //when
        assertThatNoException()
                .isThrownBy(() -> signUpService.partnerSignUp(partnerSignUpRequest));
        //then
    }

    @DisplayName("check already exists nickname")
    @Test
    void isExistsNickname() {
        //given
        String nickname = "nickname";
        given(memberRepository.existsByNickname(anyString()))
                .willReturn(false);
        //when
        assertThatNoException()
                .isThrownBy(() -> signUpService.isExistsNickname(nickname));
        //then
    }

    @DisplayName("check already exists store name")
    @Test
    void isExistsStoreName() {
        //given
        String storeName = "store";
        given(storeRepository.existsByName(anyString()))
                .willReturn(false);
        //when
        assertThatNoException()
                .isThrownBy(() -> signUpService.isExistsStoreName(storeName));
        //then
    }

    private static SignUpRequest createSignUpRequest() {
        return new SignUpRequest(
                "username",
                "pw",
                "nickname",
                MemberRole.MEMBER,
                "zipcode",
                "street",
                "detail",
                "01011111111"
        );
    }
}
