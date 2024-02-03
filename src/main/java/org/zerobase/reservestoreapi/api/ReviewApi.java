package org.zerobase.reservestoreapi.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.aop.BindingResultHandler;
import org.zerobase.reservestoreapi.dto.MemberPrincipal;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.dto.response.ExceptionResponse;
import org.zerobase.reservestoreapi.exception.ReviewException;
import org.zerobase.reservestoreapi.exception.ValidatedException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.ReviewService;
import org.zerobase.reservestoreapi.service.StoreService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/reviews")
public class ReviewApi {
    private final StoreService storeService;
    private final ReviewService reviewService;

    @BindingResultHandler(message = "validation error during add review")
    @PostMapping
    public ResponseEntity<ReviewDto> writeReview(
            @PathVariable Long storeId,
            @RequestBody @Validated ReviewRequest reviewRequest,
            BindingResult bindingResult) {

        // TODO: check whether booking user or not -> create log table
        ReviewDto reviewDto =
                reviewService.writeReview(storeService.searchStore(storeId), reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto);
    }

    @BindingResultHandler(message = "validation error during update review")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal,
            @RequestBody @Validated ReviewRequest reviewRequest,
            BindingResult bindingResult) {

        return ResponseEntity.ok(
                reviewService.updateReview(
                        reviewRequest, reviewId, storeId, memberPrincipal.getUsername()));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        // 1. check whether writer or not
        // 2. if request user is store and target store, possible delete
        if (!reviewService.isExistsReviewByUsername(reviewId, memberPrincipal.username())
                && storeService.isExistsStoreByUsername(storeId, memberPrincipal.username())) {
            throw new ReviewException(ErrorCode.NO_AUTHORIZED);
        }
        reviewService.deleteReview(reviewId, storeId, memberPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of("You're successfully delete review."));
    }
}
