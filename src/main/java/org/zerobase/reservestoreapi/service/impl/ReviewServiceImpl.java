package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.repository.ReviewRepository;
import org.zerobase.reservestoreapi.repository.StoreRepository;
import org.zerobase.reservestoreapi.service.ReviewService;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;

  //TODO: may be not using
  @Override
  public Float getAverageRatingByStoreId(Long storeId) {
    return reviewRepository.getAverageRatingByStoreId(storeId);
  }

  @Override
  public ReviewDto writeReview(Store store, ReviewRequest reviewRequest) {
    Review review = reviewRequest.toEntity();
    store.getReviews().add(review);
    return ReviewDto.fromEntity(review);
  }

  @Override
  public ReviewDto updateReview(ReviewRequest reviewRequest, Long reviewId) {
    // TODO: check whether writer or not
    Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
    if (StringUtils.hasText(reviewRequest.content())) {
      review.setContent(reviewRequest.content());
    }
    if (!Objects.isNull(reviewRequest.rating())) {
      review.setRating(reviewRequest.rating());
    }
    return ReviewDto.fromEntity(review);
  }

  @Override
  public void deleteReview(Long reviewId) {
    // TODO: check writer or store
    reviewRepository.deleteById(reviewId);
  }
}
