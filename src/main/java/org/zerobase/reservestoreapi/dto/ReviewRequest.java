package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Review;

//TODO: validation
public record ReviewRequest(
        String content,
        Integer rating
) {
    public Review toEntity() {
        return Review.of(content, rating);
    }
}
