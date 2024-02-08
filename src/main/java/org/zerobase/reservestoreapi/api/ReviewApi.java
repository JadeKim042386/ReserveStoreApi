package org.zerobase.reservestoreapi.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.aop.BindingResultHandler;
import org.zerobase.reservestoreapi.dto.MemberPrincipal;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.exception.ReviewException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.ReviewService;
import org.zerobase.reservestoreapi.service.StoreService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/reviews")
public class ReviewApi {
    private final MemberService memberService;
    private final StoreService storeService;
    private final ReviewService reviewService;

    /**
     * Retrieve all reviews
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/reviews?sort=createdAt,asc
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "content": List<ReviewDto>,
     *     "number": 0,
     *     "size": 10,
     *     "sort": [
     *         {
     *             "direction": "DESC",
     *             "property": "createdAt",
     *             "ignoreCase": false,
     *             "nullHandling": "NATIVE",
     *             "descending": false,
     *             "ascending": true
     *         }
     *     ],
     *     "totalElements": 0,
     *     "totalPages": 0,
     *     "first": true,
     *     "last": true
     * }
     * </pre>
     */
    @Operation(summary = "Retrieve all reviews")
    @GetMapping
    public ResponseEntity<PagedResponse<ReviewDto>> getReviews(
            @PathVariable Long storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return ResponseEntity.ok(reviewService.searchReviewDtoByStoreId(storeId, pageable));
    }

    /**
     * Write Review
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/reviews?content=good&rating=5
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "id": 1,
     *     "content": "good",
     *     "rating": 5,
     *     "createdAt": 2024-02-08T17:02:53,
     *     "modifiedAt": 2024-02-08T17:02:53,
     *     "createdBy": admin,
     *     "modifiedBy": admin
     * }
     * </pre>
     *
     * @throws ReviewException if already exists booking ({@code ErrorCode.ALREADY_EXISTS_REVIEW})
     */
    @Operation(summary = "Write Review")
    @BindingResultHandler(message = "validation error during add review")
    @PostMapping
    public ResponseEntity<ReviewDto> writeReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewRequest reviewRequest,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal,
            BindingResult bindingResult) {

        // is there exists wrote review before?
        if (reviewService.isExistsReviewByStoreIdAndUsername(storeId, memberPrincipal.username())) {
            throw new ReviewException(ErrorCode.ALREADY_EXISTS_REVIEW);
        }
        ReviewDto reviewDto =
                reviewService.writeReview(storeService.searchStore(storeId), reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto);
    }

    /**
     * Update Review
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/reviews/1?content=dirty&rating=1
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "id": 1,
     *     "content": "dirty",
     *     "rating": 1,
     *     "createdAt": 2024-02-08T17:02:53,
     *     "modifiedAt": 2024-02-08T17:12:53,
     *     "createdBy": admin,
     *     "modifiedBy": admin
     * }
     * </pre>
     */
    @Operation(summary = "Update Review")
    @BindingResultHandler(message = "validation error during update review")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal,
            @RequestBody @Valid ReviewRequest reviewRequest,
            BindingResult bindingResult) {

        return ResponseEntity.ok(
                reviewService.updateReview(
                        reviewRequest, reviewId, storeId, memberPrincipal.getUsername()));
    }

    /**
     * Delete Review
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/reviews/1
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "message": "You're successfully delete review."
     * }
     * </pre>
     *
     * @throws ReviewException if not writer and admin store ({@code ErrorCode.NO_AUTHORIZED})
     */
    @Operation(summary = "Delete Review")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        // 1. check whether writer or not
        // 2. if request user is store and target store, possible delete
        if (!reviewService.isExistsReviewByUsername(reviewId, memberPrincipal.username())
                && !memberService.isExistsByUsernameAndStoreId(
                        memberPrincipal.username(), storeId)) {
            throw new ReviewException(ErrorCode.NO_AUTHORIZED);
        }
        reviewService.deleteReview(reviewId, storeId, memberPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of("You're successfully delete review."));
    }
}
