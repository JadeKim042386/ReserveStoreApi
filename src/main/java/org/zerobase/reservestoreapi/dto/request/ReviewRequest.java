package org.zerobase.reservestoreapi.dto.request;

import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.domain.Store;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ReviewRequest(@NotBlank String content, @NotNull @Min(0) @Max(5) Integer rating) {
    public Review toEntity(Store store) {
        return Review.of(content, rating, store);
    }
}
