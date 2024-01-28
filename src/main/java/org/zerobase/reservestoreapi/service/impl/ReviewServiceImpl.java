package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.ReviewRequest;
import org.zerobase.reservestoreapi.repository.ReviewRepository;
import org.zerobase.reservestoreapi.service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public Float getAverageRatingByStoreId(Long storeId) {
        return reviewRepository.getAverageRatingByStoreId(storeId);
    }

    @Override
    public ReviewDto writeReview(ReviewRequest reviewRequest) {
        return null;
    }

    @Override
    public ReviewDto updateReview(ReviewRequest reviewRequest) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

    }
}
