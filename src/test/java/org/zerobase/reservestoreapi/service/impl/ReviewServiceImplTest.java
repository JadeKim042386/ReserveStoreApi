package org.zerobase.reservestoreapi.service.impl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.ReviewRequest;
import org.zerobase.reservestoreapi.repository.ReviewRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @InjectMocks private ReviewServiceImpl reviewService;
    @Mock private ReviewRepository reviewRepository;

    @DisplayName("get average rating")
    @Test
    void getAverageRatingByStoreId() {
        //given
        Long storeId = 1L;
        given(reviewRepository.getAverageRatingByStoreId(anyLong()))
                .willReturn(3.4f);
        //when
        Float rating = reviewService.getAverageRatingByStoreId(storeId);
        //then
        assertThat(rating).isEqualTo(3.4f);
    }

    @DisplayName("write review")
    @Test
    void writeReview() {
        //given
        ReviewRequest reviewRequest = new ReviewRequest("content", 5);
        //when
        ReviewDto reviewDto = reviewService.writeReview(reviewRequest);
        //then
    }

    @DisplayName("update review")
    @Test
    void updateReview() {
        //given
        ReviewRequest reviewRequest = new ReviewRequest("update content", 5);
        //when
        ReviewDto updatedReviewDto = reviewService.updateReview(reviewRequest);
        //then
    }

    @DisplayName("delete review")
    @Test
    void deleteReview() {
        //given
        Long reviewId = 1L;
        //when
        reviewService.deleteReview(reviewId);
        //then
    }
}