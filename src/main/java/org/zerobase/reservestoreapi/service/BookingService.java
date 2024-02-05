package org.zerobase.reservestoreapi.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.dto.BookingDto;

import java.time.LocalDateTime;

public interface BookingService {
    /** Request booking by memberId and storeId */
    BookingDto requestBooking(String phone, String username, Long storeId, LocalDateTime requestTime);

    /**
     * Check visit booked store before 10 minutes and delete. if you're late, cancel your booking.
     */
    void checkVisit(String username, Long storeId);

    /** Get bookings info by date(e.g.2024-01-01) */
    Page<BookingDto> searchBookingsByDate(Long storeId, Predicate predicate, Pageable pageable);

    /** Approve or Deny for request booking */
    void confirmBooking(Long bookingId, Boolean isApprove, Long storeId);
}
