package org.zerobase.reservestoreapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
  @InjectMocks private MemberServiceImpl memberService;
  @Mock private MemberRepository memberRepository;

  @Test
  void searchMemberByUsername() {
    // given
    String username = "username";
    given(memberRepository.findByUsername(anyString()))
        .willReturn(
            Optional.of(
                Member.ofMember(
                    "admin",
                    "pw",
                    "nickname",
                    MemberRole.ADMIN,
                    Address.of("zipcode", "street", "detail"),
                    "01011111111")));
    // when
    Member member = memberService.searchMemberByUsername(username);
    // then
    assertThat(member.getUsername()).isEqualTo("admin");
    assertThat(member.getNickname()).isEqualTo("nickname");
  }

  @DisplayName("check already exists nickname")
  @Test
  void isExistsNickname() {
    // given
    String nickname = "nickname";
    given(memberRepository.existsByNickname(anyString())).willReturn(false);
    // when
    assertThatNoException().isThrownBy(() -> memberService.isExistsNickname(nickname));
    // then
  }
}
