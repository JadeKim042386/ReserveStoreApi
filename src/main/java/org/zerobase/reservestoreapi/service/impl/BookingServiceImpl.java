package org.zerobase.reservestoreapi.service.impl;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.exception.BookingException;
import org.zerobase.reservestoreapi.exception.StoreException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.repository.BookingRepository;
import org.zerobase.reservestoreapi.service.BookingService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public BookingDto requestBooking(String username, Long storeId, LocalDateTime requestTime) {
        // 1. if already exists booking at requestTime, you can't
        // 2. if already exists booking by username, you can't
        if (bookingRepository.existsCreateByStoreId(requestTime, username, storeId)) {
            throw new BookingException(ErrorCode.ALREADY_EXISTS_BOOKING);
        }
        // add booking
        return BookingDto.fromEntity(bookingRepository.save(Booking.of(false)));
    }

    @Override
    public void checkVisit(String username, Long storeId) {
        Booking booking =
                bookingRepository
                        .findByCreatedByAndStoreId(username, storeId)
                        .orElseThrow(() -> new BookingException(ErrorCode.NOT_FOUND_ENTITY));
        // delete booking
        bookingRepository.delete(booking);
        // check whether approve or not
        if (!booking.getApprove()) {
            throw new BookingException(ErrorCode.NO_APPROVAL_BOOKING);
        }
        // check visit before 10 minutes
        if (booking.getCreatedAt().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new BookingException(ErrorCode.LATE_VISIT);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookingDto> searchBookingsByDate(
            Long storeId, Predicate predicate, Pageable pageable) {

        return bookingRepository
                .findAllByStoreId(storeId, predicate, pageable)
                .map(BookingDto::fromEntity);
    }

    @Override
    public void confirmBooking(Long bookingId, Boolean isApprove, Long storeId) {
        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() -> new BookingException(ErrorCode.NOT_FOUND_ENTITY));
        // check requested store and booking store match
        if (!Objects.equals(booking.getStore().getId(), storeId)) {
            throw new StoreException(ErrorCode.NOT_MATCH_STORE);
        }
        // approval when isApprove is true
        if (isApprove) {
            booking.approval();
            log.debug("you're successfully approve booking {}", bookingId);
        }
        // delete(reject) when isApprove is false
        else {
            bookingRepository.delete(booking);
            log.debug("you're successfully denied booking {}", bookingId);
        }
    }
}
