package org.zerobase.reservestoreapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.dto.BookingDto;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    /**
     * Request booking by memberId and storeId
     */
    BookingDto requestBooking(String username, Long storeId, LocalDateTime requestTime);

    /**
     * Check visit booked store before 10 minutes and delete.
     * if you're late, cancel your booking.
     */
    void checkVisit(String username, Long storeId);

    /**
     * Get bookings info by date(e.g.2024-01-01)
     */
    Page<BookingDto> searchBookingsByDate(LocalDateTime date, Long storeId, Pageable pageable);

    /**
     * Approve or Deny for request booking
     */
    void confirmBooking(Long storeId, Boolean isApprove, String storeName);
}
