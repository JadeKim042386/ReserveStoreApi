package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Review;

public record ReviewDto(
        String content,
        Integer rating,
        UserDateAudit userDateAudit
) {
    public static ReviewDto fromEntity(Review review) {
        return new ReviewDto(
                review.getContent(),
                review.getRating(),
                UserDateAudit.of(
                        review.getCreatedAt(),
                        review.getModifiedAt(),
                        review.getCreatedBy(),
                        review.getModifiedBy()
                )
        );
    }
}
