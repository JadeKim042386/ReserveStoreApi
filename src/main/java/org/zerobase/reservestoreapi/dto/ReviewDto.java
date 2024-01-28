package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.dto.auditing.UserDateAllAudit;

public record ReviewDto(
        Long id,
        String content,
        Integer rating,
        UserDateAllAudit userDateAllAudit
) {
    public static ReviewDto fromEntity(Review review) {
        return new ReviewDto(
                review.getId(),
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
