package org.zerobase.reservestoreapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.dto.MemberPrincipal;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.exception.BookingException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.BookingService;
import org.zerobase.reservestoreapi.service.MemberService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/bookings")
public class BookingApi {
    private final BookingService bookingService;
    private final MemberService memberService;

    /**
     * Look up booking info for a specific date for a specific store. Returns Page based on
     * SSR(Server Side Rendering) (e.g.thymeleaf). Therefore, the return type may change in the
     * future.
     */
    @GetMapping
    public ResponseEntity<Page<BookingDto>> searchBookingsByDate(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable Long storeId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate queryDate) {

        return ResponseEntity.ok(
                bookingService.searchBookingsByDate(queryDate.atTime(0, 0, 0), storeId, pageable));
    }

    /**
     * Request booking for a specific time for a specific store.
     *
     * <p>throw an exception followed: 1. if requested user wasn't sign up 2. if already exists
     * booking at request booking time 3. if already exists booking by request user
     */
    @PostMapping
    public ResponseEntity<BookingDto> requestBooking(
            @PathVariable Long storeId,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime requestBookingTime,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        if (memberService.isExistsUsername(memberPrincipal.getUsername())) {
            throw new BookingException(ErrorCode.ALREADY_EXISTS_USERNAME_OR_NICKNAME);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        bookingService.requestBooking(
                                memberPrincipal.username(), storeId, requestBookingTime));
    }

    /**
     * store can deny or approve the booking. if the requested store and the booking store do not
     * match, throw exception.
     */
    @PutMapping("/{bookingId}")
    public ResponseEntity<ApiResponse> confirmBooking(
            @PathVariable Long storeId,
            @PathVariable Long bookingId,
            @RequestParam Boolean isApprove) {

        bookingService.confirmBooking(bookingId, isApprove, storeId);
        return ResponseEntity.ok(ApiResponse.of("you're successfully confirm."));
    }

    /**
     * when a user visits the store, can request a visit check. if the user wasn't arrive 10 minutes
     * earlier than the booking time, throw exception.
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> checkVisit(
            @PathVariable Long storeId, @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        bookingService.checkVisit(memberPrincipal.username(), storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of("you're successfully check visit."));
    }
}
