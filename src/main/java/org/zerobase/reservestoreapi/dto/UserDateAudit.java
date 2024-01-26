package org.zerobase.reservestoreapi.dto;

public record UserDateAudit(
        String createdAt,
        String modifiedAt,
        String createdBy,
        String modifiedBy
) {
}
