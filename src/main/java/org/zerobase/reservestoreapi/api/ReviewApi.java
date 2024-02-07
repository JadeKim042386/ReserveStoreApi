package org.zerobase.reservestoreapi.api;

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

    /** Retrieve all reviews */
    @GetMapping
    public ResponseEntity<PagedResponse<ReviewDto>> getReviews(
            @PathVariable Long storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return ResponseEntity.ok(reviewService.searchReviewDtoByStoreId(storeId, pageable));
    }

    /** Write Review */
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

    /** Update Review */
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

    /** Delete Review */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        // 1. check whether writer or not
        // 2. if request user is store and target store, possible delete
        if (!reviewService.isExistsReviewByUsername(reviewId, memberPrincipal.username())
                && !memberService.isExistsByUsernameAndStoreId(memberPrincipal.username(), storeId)) {
            throw new ReviewException(ErrorCode.NO_AUTHORIZED);
        }
        reviewService.deleteReview(reviewId, storeId, memberPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of("You're successfully delete review."));
    }
}
