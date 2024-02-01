package org.zerobase.reservestoreapi.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.request.ReviewRequest;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.service.ReviewService;
import org.zerobase.reservestoreapi.service.StoreService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
//TODO: bindingResult AOP handle
public class ReviewApi {
    private final StoreService storeService;
    private final ReviewService reviewService;

    @PostMapping("/{storeId}")
    public ResponseEntity<?> writeReview(
            @PathVariable Long storeId,
            @RequestBody @Validated ReviewRequest reviewRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.error("bindingResult: {}", bindingResult);
            //TODO: exception handle
            throw new RuntimeException();
        }
        // TODO: check whether booking user or not -> create log table
        ReviewDto reviewDto = reviewService.writeReview(storeService.searchStore(storeId), reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewDto);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestBody @Validated ReviewRequest reviewRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.error("bindingResult: {}", bindingResult);
            //TODO: exception handle
            throw new RuntimeException();
        }
        return ResponseEntity.ok(
                reviewService.updateReview(reviewRequest, reviewId)
        );
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of("You're successfully delete review."));
    }
}
