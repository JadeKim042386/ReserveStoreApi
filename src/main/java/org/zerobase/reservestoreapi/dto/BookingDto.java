package org.zerobase.reservestoreapi.dto;

public record BookingDto(
        Boolean approve,
        UserDateAudit userDateAudit
) {
}
