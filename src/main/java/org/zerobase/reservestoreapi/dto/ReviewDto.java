package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Review;

public record ReviewDto(
        String content,
        Integer rating,
        UserDateAllAudit userDateAllAudit
) {
    public static ReviewDto fromEntity(Review review) {
        return new ReviewDto(
                review.getContent(),
                review.getRating(),
                UserDateAllAudit.of(
                        review.getCreatedAt(),
                        review.getModifiedAt(),
                        review.getCreatedBy(),
                        review.getModifiedBy()
                )
        );
    }
}
