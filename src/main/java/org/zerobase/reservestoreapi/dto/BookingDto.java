package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.auditing.UserDateCreatedAudit;

public record BookingDto(Boolean approve, String phone, UserDateCreatedAudit userDateCreatedAudit) {
    public static BookingDto fromEntity(Booking booking) {
        return new BookingDto(
                booking.getApprove(),
                booking.getPhone(),
                UserDateCreatedAudit.of(booking.getCreatedAt(), booking.getCreatedBy()));
    }
}
