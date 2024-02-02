package org.zerobase.reservestoreapi.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.security.SecurityConfig;
import org.zerobase.reservestoreapi.service.MemberService;

import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean private MemberService memberService;

    @BeforeTestMethod
    public void securitySetUp() {
        given(memberService.searchMemberByUsername("testUser"))
                .willReturn(
                        Member.ofMember(
                                "testUser",
                                "pw",
                                "testUser",
                                MemberRole.MEMBER,
                                Address.of("zipcode", "street", "detail"),
                                "01011111111"));
        given(memberService.searchMemberByUsername("testStore"))
                .willReturn(
                        Member.ofMember(
                                "testStore",
                                "pw",
                                "testStore",
                                MemberRole.STORE,
                                Address.of("zipcode", "street", "detail"),
                                "01011111111"));
    }
}
