package org.zerobase.reservestoreapi.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record UserDateAudit(
        String createdAt,
        String modifiedAt,
        String createdBy,
        String modifiedBy
) {
    public static UserDateAudit of(LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy) {
        return new UserDateAudit(
                dateTimeToString(createdAt),
                dateTimeToString(modifiedAt),
                createdBy,
                modifiedBy
        );
    }

    private static String dateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }
}
