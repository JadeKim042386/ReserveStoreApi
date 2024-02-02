package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;

public interface ReviewService {
  /** Add review for visited store */
  ReviewDto writeReview(Store store, ReviewRequest reviewRequest);

  /** Update review */
  ReviewDto updateReview(ReviewRequest reviewRequest, Long reviewId, Long storeId);

  /** Delete review */
  void deleteReview(Long reviewId, Long storeId);
}
