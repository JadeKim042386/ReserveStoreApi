package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.repository.ReviewRepository;
import org.zerobase.reservestoreapi.repository.StoreReviewInfoRepository;
import org.zerobase.reservestoreapi.service.ReviewService;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;
  private final StoreReviewInfoRepository storeReviewInfoRepository;

  @Override
  public ReviewDto writeReview(Store store, ReviewRequest reviewRequest) {
    ReviewDto reviewDto =
        ReviewDto.fromEntity(reviewRepository.save(reviewRequest.toEntity(store)));
    updateStoreReviewInfo(store.getId());
    return reviewDto;
  }

  @Override
  public ReviewDto updateReview(ReviewRequest reviewRequest, Long reviewId, Long storeId) {
    // TODO: check whether writer or not
    Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
    if (StringUtils.hasText(reviewRequest.content())
        && !reviewRequest.content().equals(review.getContent())) {
      review.setContent(reviewRequest.content());
    }
    if (!Objects.isNull(reviewRequest.rating())
        && !Objects.equals(reviewRequest.rating(), review.getRating())) {
      review.setRating(reviewRequest.rating());
      updateStoreReviewInfo(storeId);
    }

    return ReviewDto.fromEntity(review);
  }

  @Override
  public void deleteReview(Long reviewId, Long storeId) {
    // TODO: check writer or store
    reviewRepository.deleteById(reviewId);
    updateStoreReviewInfo(storeId);
  }

  /** Update average rating, review count of specific store when write/update/delete review */
  private void updateStoreReviewInfo(Long storeId) {
    storeReviewInfoRepository.updateStoreReviewInfoByStoreId(storeId);
  }
}
