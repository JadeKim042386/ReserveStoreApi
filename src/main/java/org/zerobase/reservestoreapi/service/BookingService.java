package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.dto.BookingDto;

public interface BookingService {
    /**
     * Request booking by memberId and storeId
     */
    BookingDto requestBooking(String username, Long storeId);

    /**
     * Check visit booked store before 10 minutes and delete.
     * if you're late, cancel your booking.
     */
    void checkVisit(String username, Long storeId);
}
