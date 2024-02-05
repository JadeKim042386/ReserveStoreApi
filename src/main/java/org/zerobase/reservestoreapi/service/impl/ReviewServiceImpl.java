package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.exception.ReviewException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.repository.ReviewRepository;
import org.zerobase.reservestoreapi.repository.StoreReviewInfoRepository;
import org.zerobase.reservestoreapi.service.ReviewService;

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
    public ReviewDto updateReview(
            ReviewRequest reviewRequest, Long reviewId, Long storeId, String requestUsername) {
        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewException(ErrorCode.NOT_FOUND_ENTITY));
        // check whether writer or not
        if (!review.getCreatedBy().equals(requestUsername)) {
            throw new ReviewException(ErrorCode.NOT_WRITER);
        }
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
    public void deleteReview(Long reviewId, Long storeId, String requestUsername) {
        reviewRepository.deleteById(reviewId);
        updateStoreReviewInfo(storeId);
    }

    @Override
    public boolean isExistsReviewByUsername(Long reviewId, String username) {
        return reviewRepository.existsByIdAndCreatedBy(reviewId, username);
    }

    @Override
    public boolean isExistsReviewByStoreIdAndUsername(Long storeId, String username) {
        return reviewRepository.existsByStoreIdAndCreatedBy(storeId, username);
    }

    /** Update average rating, review count of specific store when write/update/delete review */
    private void updateStoreReviewInfo(Long storeId) {
        storeReviewInfoRepository.updateStoreReviewInfoByStoreId(storeId);
    }
}
