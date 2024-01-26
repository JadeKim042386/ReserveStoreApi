package org.zerobase.reservestoreapi.dto;

import java.util.Set;

public record StoreWithReviewDto(
        StoreDto storeDto,
        Set<ReviewDto> reviews
) {
}
