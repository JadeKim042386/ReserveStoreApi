package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Booking;

public record BookingDto(
        Boolean approve,
        UserDateCreatedAudit userDateCreatedAudit
) {
    public static BookingDto fromEntity(Booking booking) {
        return new BookingDto(
                booking.getApprove(),
                UserDateCreatedAudit.of(booking.getCreatedAt(), booking.getCreatedBy())
        );
    }
}
