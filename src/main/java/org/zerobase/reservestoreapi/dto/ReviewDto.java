package org.zerobase.reservestoreapi.dto;

public record ReviewDto(
        String content,
        Integer rating,
        UserDateAudit userDateAudit
) {
}
