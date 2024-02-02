package org.zerobase.reservestoreapi.api;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zerobase.reservestoreapi.config.TestSecurityConfig;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.service.BookingService;
import org.zerobase.reservestoreapi.service.MemberService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@WebMvcTest(BookingApi.class)
class BookingApiTest {
    @Autowired private MockMvc mvc;
    @MockBean private BookingService bookingService;
    @Mock private MemberService memberService;

    @Test
    void searchBookingsByDate() throws Exception {
        // given
        Long storeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        given(bookingService.searchBookingsByDate(any(), anyLong(), any()))
                .willReturn(
                        new PageImpl<>(
                                List.of(BookingDto.fromEntity(createBooking(LocalDateTime.now()))),
                                pageable,
                                1));
        // when
        mvc.perform(
                        get("/api/v1/stores/" + storeId + "/bookings")
                                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1));
        // then
    }

    @WithUserDetails(value = "testUser", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void requestBooking() throws Exception {
        // given
        Long storeId = 1L;
        given(memberService.isExistsUsername(anyString())).willReturn(false);
        given(bookingService.requestBooking(anyString(), anyLong(), any()))
                .willReturn(BookingDto.fromEntity(createBooking(LocalDateTime.now())));
        // when
        mvc.perform(
                        post("/api/v1/stores/" + storeId + "/bookings")
                                .param("time", LocalDate.now().atTime(10, 0, 0).toString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.approve").value(false));

        // then
    }

    @WithUserDetails(value = "testStore", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void confirmBooking() throws Exception {
        // given
        Long storeId = 1L;
        Long bookingId = 1L;
        // when
        mvc.perform(
                        put("/api/v1/stores/" + storeId + "/bookings/" + bookingId)
                                .param("isApprove", "true")
                                .param("storeName", "testStore"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // then
    }

    @WithUserDetails(value = "testUser", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void checkVisit() throws Exception {
        // given
        Long storeId = 1L;
        // when
        mvc.perform(delete("/api/v1/stores/" + storeId + "/bookings"))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // then
    }

    private static Booking createBooking(LocalDateTime now) throws IllegalAccessException {
        Booking booking = Booking.of(false);
        FieldUtils.writeField(booking, "createdAt", now.plusMinutes(11), true);
        FieldUtils.writeField(booking, "createdBy", "admin", true);
        return booking;
    }
}
