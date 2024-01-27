package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
