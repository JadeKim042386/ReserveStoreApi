package org.zerobase.reservestoreapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zerobase.reservestoreapi.dto.request.PartnerSignUpRequest;
import org.zerobase.reservestoreapi.dto.request.SignUpRequest;
import org.zerobase.reservestoreapi.security.SecurityConfig;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.SignUpService;
import org.zerobase.reservestoreapi.service.StoreService;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Import(SecurityConfig.class)
@WebMvcTest(SignUpApi.class)
class SignUpApiTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @MockBean private SignUpService signUpService;
    @MockBean private MemberService memberService;
    @MockBean private StoreService storeService;

    @DisplayName("request member sign up")
    @Test
    void requestMemberSignUp() throws Exception {
        // given
        willDoNothing().given(signUpService).signUp(any());
        // when
        mvc.perform(
                        post("/api/v1/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(createMemberSignUpRequest()))
                                .param("partner", "false"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // then
        then(signUpService).should().signUp(any());
    }

    @DisplayName("request store sign up")
    @Test
    void requestStoreSignUp() throws Exception {
        // given
        willDoNothing().given(signUpService).partnerSignUp(any());
        // when
        mvc.perform(
                        post("/api/v1/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(createPartnerSignUpRequest()))
                                .param("partner", "true"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // then
        then(signUpService).should().partnerSignUp(any());
    }

    @DisplayName("check exists username/nickname/storeName")
    @Test
    void checkExists() throws Exception {
        // given
        given(memberService.isExistsUsername(anyString())).willReturn(false);
        // when
        mvc.perform(get("/api/v1/signup/exists").param("username", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // then
    }

    private static SignUpRequest createMemberSignUpRequest() {
        return new SignUpRequest(
                "username",
                "pw",
                "nickname",
                "MEMBER",
                "11111",
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
                "11111",
                "street",
                "detail",
                "01011111111",
                new PartnerSignUpRequest(
                        "store", LocalTime.of(9, 0), LocalTime.of(18, 0), 30, "BAR"));
    }
}
