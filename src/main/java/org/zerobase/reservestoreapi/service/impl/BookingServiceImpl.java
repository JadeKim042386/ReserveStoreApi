package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.repository.BookingRepository;
import org.zerobase.reservestoreapi.service.BookingService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public BookingDto requestBooking(String username, Long storeId, LocalDateTime requestTime) {
        //TODO: required sign up
        //1. if already exists booking at requestTime, you can't
        //2. if already exists booking by username, you can't
        if (bookingRepository.existsByCreatedAtAndStoreId(requestTime, storeId)
                || bookingRepository.existsByCreatedByAndStoreId(username, storeId)) {
            //TODO: handling exception
            throw new RuntimeException("already exists booking");
        }
        //add booking
        return BookingDto.fromEntity(
                bookingRepository.save(
                        Booking.of(false)
                )
        );
    }

    @Override
    public void checkVisit(String username, Long storeId) {
        Booking booking = bookingRepository.findByCreatedByAndStoreId(username, storeId)
                .orElseThrow(EntityNotFoundException::new);
        //TODO: check whether approve or not
        //check visit before 10 minutes
        if (booking.getCreatedAt().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("you're late. you should be visit before 10 minutes.");
        }
        //delete booking
        bookingRepository.delete(booking);
    }

    @Override
    public List<BookingDto> searchBookingsByDate(LocalDate date) {
        return null;
    }
}
