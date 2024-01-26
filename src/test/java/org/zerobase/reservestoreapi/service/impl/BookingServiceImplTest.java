package org.zerobase.reservestoreapi.service.impl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @InjectMocks private BookingServiceImpl bookingService;
    @Mock private BookingRepository bookingRepository;

    @DisplayName("Request booking by memberId and storeId")
    @Test
    void requestBooking() throws IllegalAccessException {
        //given
        String username = "admin";
        Long storeId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = createBooking(now);
        given(bookingRepository.existsByCreatedAtAndStoreId(any(), anyLong())).willReturn(false);
        given(bookingRepository.existsByCreatedByAndStoreId(anyString(), anyLong())).willReturn(false);
        given(bookingRepository.save(any())).willReturn(booking);
        //when
        BookingDto bookingDto = bookingService.requestBooking(username, storeId, now);
        //then
        assertThat(bookingDto.approve()).isEqualTo(false);
    }

    @DisplayName("Check visit booked store before 10 minutes and delete")
    @Test
    void checkVisit() throws IllegalAccessException {
        //given
        String username = "admin";
        Long storeId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = createBooking(now);
        given(bookingRepository.findByCreatedByAndStoreId(anyString(), anyLong()))
                .willReturn(Optional.of(booking));
        willDoNothing().given(bookingRepository).delete(any());
        //when
        bookingService.checkVisit(username, storeId);
        //then
    }

    private static Booking createBooking(LocalDateTime now) throws IllegalAccessException {
        Booking booking = Booking.of(false);
        FieldUtils.writeField(booking,"createdAt", now.plusMinutes(11), true);
        FieldUtils.writeField(booking,"createdBy", "admin", true);
        return booking;
    }
}