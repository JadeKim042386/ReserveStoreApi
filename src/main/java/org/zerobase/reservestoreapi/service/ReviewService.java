package org.zerobase.reservestoreapi.service;

public interface ReviewService {
    /**
     * Get average rating of all reviews (0.0 ~ 5.0)
     */
    Float getAverageRatingByStoreId(Long storeId);
}
