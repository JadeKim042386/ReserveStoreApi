package org.zerobase.reservestoreapi.service.impl;

import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.domain.QBooking;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.repository.BookingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @InjectMocks private BookingServiceImpl bookingService;
    @Mock private BookingRepository bookingRepository;

    @DisplayName("Request booking by memberId and storeId")
    @Order(0)
    @Test
    void requestBooking() throws IllegalAccessException {
        // given
        String phone = "01011111111";
        String username = "admin";
        Long storeId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = createBooking(now);
        given(bookingRepository.existsCreateByStoreId(any(), anyString(), anyLong()))
                .willReturn(false);
        given(bookingRepository.save(any())).willReturn(booking);
        // when
        BookingDto bookingDto = bookingService.requestBooking(phone, username, storeId, now);
        // then
        assertThat(bookingDto.approve()).isEqualTo(false);
    }

    @DisplayName("Check visit booked store before 10 minutes and delete")
    @Order(1)
    @Test
    void checkVisit() throws IllegalAccessException {
        // given
        String username = "admin";
        Long storeId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = createBooking(now);
        FieldUtils.writeField(booking, "approve", true, true);
        given(bookingRepository.findByCreatedByAndStoreId(anyString(), anyLong()))
                .willReturn(Optional.of(booking));
        willDoNothing().given(bookingRepository).delete(any());
        // when
        bookingService.checkVisit(username, storeId);
        // then
    }

    @DisplayName("search bookings by date")
    @Order(2)
    @Test
    void searchBookingsByDate() throws IllegalAccessException {
        // given
        LocalDateTime now = LocalDate.now().atTime(0, 0, 0);
        Pageable pageable = PageRequest.of(0, 10);
        Boolean approve = true;
        Predicate predicate = QBooking.booking.approve.eq(approve);
        given(bookingRepository.findAllByStoreId(anyLong(), any(), any()))
                .willReturn(new PageImpl<>(List.of(createBooking(now)), pageable, 1));
        // when
        PagedResponse<BookingDto> bookingDtos =
                bookingService.searchBookingsByDate(1L, predicate, pageable);
        // then
        assertThat(bookingDtos.totalElements()).isEqualTo(1);
        assertThat(bookingDtos.content().get(0).userDateCreatedAudit().createdBy())
                .isEqualTo("admin");
    }

    @DisplayName("confirm booking when approve")
    @Order(3)
    @Test
    void confirmBooking_approve() throws IllegalAccessException {
        // given
        Long bookingId = 1L;
        Long storeId = 1L;
        Boolean isApprove = true;
        Booking booking = createBooking(LocalDateTime.now());
        given(bookingRepository.findById(anyLong())).willReturn(Optional.of(booking));
        // when
        assertThatNoException()
                .isThrownBy(() -> bookingService.confirmBooking(bookingId, isApprove, storeId));
        // then
        assertThat(booking.getApprove()).isEqualTo(true);
    }

    @DisplayName("confirm booking when reject")
    @Order(4)
    @Test
    void confirmBooking_reject() throws IllegalAccessException {
        // given
        Long bookingId = 1L;
        Long storeId = 1L;
        Boolean isApprove = false;
        Booking booking = createBooking(LocalDateTime.now());
        given(bookingRepository.findById(anyLong())).willReturn(Optional.of(booking));
        willDoNothing().given(bookingRepository).delete(any());
        // when
        assertThatNoException()
                .isThrownBy(() -> bookingService.confirmBooking(bookingId, isApprove, storeId));
        // then
    }

    private static Booking createBooking(LocalDateTime now) throws IllegalAccessException {
        Booking booking = Booking.of("01011111111", false);
        FieldUtils.writeField(booking, "createdAt", now.plusMinutes(11), true);
        FieldUtils.writeField(booking, "createdBy", "admin", true);
        FieldUtils.writeField(booking, "store", createStore(), true);
        return booking;
    }

    private static Store createStore() throws IllegalAccessException {
        Store store =
                Store.of(
                        "store", LocalTime.of(9, 0), LocalTime.of(18, 0), 30, 11.1f, StoreType.BAR);
        FieldUtils.writeField(store, "id", 1L, true);
        return store;
    }
}
