package org.zerobase.reservestoreapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zerobase.reservestoreapi.config.TestSecurityConfig;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.service.ReviewService;
import org.zerobase.reservestoreapi.service.StoreService;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@WebMvcTest(ReviewApi.class)
class ReviewApiTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @MockBean private StoreService storeService;
    @MockBean private ReviewService reviewService;

    @WithUserDetails(value = "testUser", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("write review")
    @Test
    void writeReview() throws Exception {
        // given
        Long storeId = 1L;
        given(storeService.searchStore(anyLong())).willReturn(createStore());
        given(reviewService.writeReview(any(), any())).willReturn(createReviewDto());
        // when
        mvc.perform(
                        post("/api/v1/stores/" + storeId + "/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(createReviewRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("content"));
        // then
    }

    @WithUserDetails(value = "testUser", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("update review")
    @Test
    void updateReview() throws Exception {
        // given
        Long storeId = 1L;
        Long reviewId = 1L;
        given(reviewService.updateReview(any(), anyLong(), anyLong(), anyString()))
                .willReturn(createReviewDto());
        // when
        mvc.perform(
                        put("/api/v1/stores/" + storeId + "/reviews/" + reviewId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(createReviewRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("content"));
        // then
    }

    @WithUserDetails(value = "testUser", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("delete review")
    @Test
    void deleteReview() throws Exception {
        // given
        Long storeId = 1L;
        Long reviewId = 1L;
        willDoNothing().given(reviewService).deleteReview(anyLong(), anyLong(), anyString());
        // when
        mvc.perform(delete("/api/v1/stores/" + storeId + "/reviews/" + reviewId))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // then
    }

    private ReviewRequest createReviewRequest() {
        return new ReviewRequest("content", 3);
    }

    private ReviewDto createReviewDto() throws IllegalAccessException {
        return ReviewDto.fromEntity(createReview("content", 3));
    }

    private Review createReview(String content, Integer rating) throws IllegalAccessException {
        Review review = Review.of(content, rating, createStore());
        FieldUtils.writeField(review, "createdAt", LocalDateTime.now(), true);
        FieldUtils.writeField(review, "modifiedAt", LocalDateTime.now(), true);
        FieldUtils.writeField(review, "createdBy", "admin", true);
        FieldUtils.writeField(review, "modifiedBy", "admin", true);
        return review;
    }

    private static Store createStore() throws IllegalAccessException {
        Store store =
                Store.of("name", LocalTime.of(9, 0), LocalTime.of(18, 0), 30, StoreType.CAFFE);
        Member member =
                Member.ofMember(
                        "username",
                        "pw",
                        "nickname",
                        MemberRole.MEMBER,
                        Address.of("12345", "street", "detail"),
                        "01012341234");
        Review review = Review.of("content", 5, store);
        FieldUtils.writeField(store, "member", member, true);
        LocalDateTime now = LocalDateTime.now();
        FieldUtils.writeField(review, "createdAt", now, true);
        FieldUtils.writeField(review, "modifiedAt", now, true);
        FieldUtils.writeField(review, "createdBy", "admin", true);
        FieldUtils.writeField(review, "modifiedBy", "admin", true);
        store.getReviews().add(review);
        return store;
    }
}
