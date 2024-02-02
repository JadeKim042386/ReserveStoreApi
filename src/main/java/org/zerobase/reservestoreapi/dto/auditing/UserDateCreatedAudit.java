package org.zerobase.reservestoreapi.dto.auditing;

import org.zerobase.reservestoreapi.utils.LocalDateTimeUtils;

import java.time.LocalDateTime;

public record UserDateCreatedAudit(String createdAt, String createdBy) {
    public static UserDateCreatedAudit of(LocalDateTime createdAt, String createdBy) {
        return new UserDateCreatedAudit(LocalDateTimeUtils.dateTimeToString(createdAt), createdBy);
    }
}
