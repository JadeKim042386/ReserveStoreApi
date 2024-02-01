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
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
  @InjectMocks private ReviewServiceImpl reviewService;
  @Mock private ReviewRepository reviewRepository;

  @DisplayName("get average rating")
  @Test
  void getAverageRatingByStoreId() {
    // given
    Long storeId = 1L;
    given(reviewRepository.getAverageRatingByStoreId(anyLong())).willReturn(3.4f);
    // when
    Float rating = reviewService.getAverageRatingByStoreId(storeId);
    // then
    assertThat(rating).isEqualTo(3.4f);
  }

  @DisplayName("write review")
  @Test
  void writeReview() throws IllegalAccessException {
    // given
    ReviewRequest reviewRequest = new ReviewRequest("content", 5);
    given(reviewRepository.save(any())).willReturn(createReview("content", 5));
    // when
    ReviewDto reviewDto = reviewService.writeReview(createStore(), reviewRequest);
    // then
    assertThat(reviewDto.content()).isEqualTo("content");
    assertThat(reviewDto.rating()).isEqualTo(5);
  }

  @DisplayName("update review")
  @Test
  void updateReview() throws IllegalAccessException {
    // given
    Long reviewId = 1L;
    ReviewRequest reviewRequest = new ReviewRequest("update content", 5);
    given(reviewRepository.findById(anyLong())).willReturn(Optional.of(createReview("content", 3)));
    // when
    ReviewDto updatedReviewDto = reviewService.updateReview(reviewRequest, reviewId);
    // then
    assertThat(updatedReviewDto.content()).isEqualTo("update content");
    assertThat(updatedReviewDto.rating()).isEqualTo(5);
  }

  @DisplayName("delete review")
  @Test
  void deleteReview() {
    // given
    Long reviewId = 1L;
    willDoNothing().given(reviewRepository).deleteById(anyLong());
    // when
    reviewService.deleteReview(reviewId);
    // then
  }

  private Review createReview(String content, Integer rating) throws IllegalAccessException {
    Review review = Review.of(content, rating);
    FieldUtils.writeField(review, "createdAt", LocalDateTime.now(), true);
    FieldUtils.writeField(review, "modifiedAt", LocalDateTime.now(), true);
    FieldUtils.writeField(review, "createdBy", "admin", true);
    FieldUtils.writeField(review, "modifiedBy", "admin", true);
    return review;
  }

  private Store createStore() {
    return Store.of(
            "store",
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            30,
            StoreType.BAR
    );
  }
}
