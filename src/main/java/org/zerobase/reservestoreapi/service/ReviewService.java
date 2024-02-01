package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;

public interface ReviewService {
  /** Get average rating of all reviews (0.0 ~ 5.0) for specific store */
  Float getAverageRatingByStoreId(Long storeId);

  /** Add review for visited store */
  ReviewDto writeReview(Store store, ReviewRequest reviewRequest);

  /** Update review */
  ReviewDto updateReview(ReviewRequest reviewRequest, Long reviewId);

  /** Delete review */
  void deleteReview(Long reviewId);
}
