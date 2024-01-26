package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.repository.BookingRepository;
import org.zerobase.reservestoreapi.service.BookingService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public BookingDto requestBooking(String username, Long storeId) {
        return null;
    }

    @Override
    public void checkVisit(String username, Long storeId) {

    }
}
