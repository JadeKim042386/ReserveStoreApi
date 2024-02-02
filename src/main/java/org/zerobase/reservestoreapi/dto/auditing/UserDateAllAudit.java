package org.zerobase.reservestoreapi.dto.auditing;

import org.zerobase.reservestoreapi.utils.LocalDateTimeUtils;

import java.time.LocalDateTime;

public record UserDateAllAudit(
        UserDateCreatedAudit userDateCreatedAudit, String modifiedAt, String modifiedBy) {
    public static UserDateAllAudit of(
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            String createdBy,
            String modifiedBy) {
        return new UserDateAllAudit(
                UserDateCreatedAudit.of(createdAt, createdBy),
                LocalDateTimeUtils.dateTimeToString(modifiedAt),
                modifiedBy);
    }
}
