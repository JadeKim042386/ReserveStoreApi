package org.zerobase.reservestoreapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.repository.BookingRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @InjectMocks private BookingServiceImpl bookingService;
    @Mock private BookingRepository bookingRepository;

    @DisplayName("Request booking by memberId and storeId")
    @Test
    void requestBooking() {
        //given
        String username = "admin";
        Long storeId = 1L;
        //when
        bookingService.requestBooking(username, storeId);
        //then
    }

    @DisplayName("Check visit booked store before 10 minutes and delete")
    @Test
    void checkVisit() {
        //given
        String username = "admin";
        Long storeId = 1L;
        //when
        bookingService.checkVisit(username, storeId);
        //then
    }
}