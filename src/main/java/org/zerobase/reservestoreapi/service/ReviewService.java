package org.zerobase.reservestoreapi.service;

import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;

public interface ReviewService {
    /** Add review for visited store */
    ReviewDto writeReview(Store store, ReviewRequest reviewRequest);

    /** Update review */
    ReviewDto updateReview(
            ReviewRequest reviewRequest, Long reviewId, Long storeId, String requestUsername);

    /** Delete review */
    void deleteReview(Long reviewId, Long storeId, String requestUsername);

    /** Check exists specific review had username */
    boolean isExistsReviewByUsername(Long reviewId, String username);

    /** Check exists review had store id and username */
    boolean isExistsReviewByStoreIdAndUsername(Long storeId, String username);

    /** Retrieve reviews by store id */
    PagedResponse<ReviewDto> searchReviewDtoByStoreId(Long storeId, Pageable pageable);
}
