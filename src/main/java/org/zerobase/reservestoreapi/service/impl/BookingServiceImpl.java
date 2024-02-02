package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.BookingDto;
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
    if (bookingRepository.existsByCreatedAtAndStoreId(requestTime, storeId)
        || bookingRepository.existsByCreatedByAndStoreId(username, storeId)) {
      // TODO: handling exception
      throw new RuntimeException("already exists booking");
    }
    // add booking
    return BookingDto.fromEntity(bookingRepository.save(Booking.of(false)));
  }

  @Override
  public void checkVisit(String username, Long storeId) {
    Booking booking =
        bookingRepository
            .findByCreatedByAndStoreId(username, storeId)
            .orElseThrow(EntityNotFoundException::new);
    // TODO: check whether approve or not
    // delete booking
    bookingRepository.delete(booking);
    // check visit before 10 minutes
    if (booking.getCreatedAt().minusMinutes(10).isBefore(LocalDateTime.now())) {
      throw new RuntimeException("you're late. you should be visit before 10 minutes.");
    }
  }

  @Transactional(readOnly = true)
  @Override
  public Page<BookingDto> searchBookingsByDate(
      LocalDateTime date, Long storeId, Pageable pageable) {
    // TODO: use queryDSL to filter approval
    return bookingRepository
        .findAllByCreatedAtBetweenAndStoreId(date, date.plusDays(1), storeId, pageable)
        .map(BookingDto::fromEntity);
  }

  @Override
  public void confirmBooking(Long bookingId, Boolean isApprove, Long storeId) {
    Booking booking =
        bookingRepository.findById(bookingId).orElseThrow(EntityNotFoundException::new);
    // check requested store and booking store match
    if (!Objects.equals(booking.getStore().getId(), storeId)) {
      // TODO: exception handle
      throw new RuntimeException("requested store and booking store do not match!");
    }
    // approval when isApprove is true
    if (isApprove) {
      booking.approval();
      log.debug("you're successfully approve booking {}", bookingId);
    }
    // delete(reject) when isApprove is false
    else {
      bookingRepository.delete(booking);
      log.debug("you're successfully delete booking {}", bookingId);
    }
  }
}
