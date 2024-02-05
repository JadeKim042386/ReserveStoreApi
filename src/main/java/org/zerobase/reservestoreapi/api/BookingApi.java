package org.zerobase.reservestoreapi.api;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.dto.BookingDto;
import org.zerobase.reservestoreapi.dto.MemberPrincipal;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.exception.BookingException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.BookingService;
import org.zerobase.reservestoreapi.service.MemberService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/bookings")
public class BookingApi {
    private final BookingService bookingService;

    //TODO: search all bookings

    /**
     * Look up booking info for a specific date for a specific store. Returns Page based on
     * SSR(Server Side Rendering) (e.g.thymeleaf). Therefore, the return type may change in the
     * future.
     * <pre>
     * Example:
     * - /api/v1/stores/1/bookings?approve=true&date=2024-02-03T00:00:00
     * - /api/v1/stores/1/bookings?date=2024-02-03T00:00:00
     * </pre>
     */
    @GetMapping
    public ResponseEntity<Page<BookingDto>> searchBookingsByDate(
            @PathVariable Long storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @QuerydslPredicate(root = Booking.class) Predicate predicate) {

        return ResponseEntity.ok(bookingService.searchBookingsByDate(storeId, predicate, pageable));
    }

    /**
     * Request booking for a specific time for a specific store.
     *
     * <pre>
     * throw an exception followed:
     * 1. if already exists booking at request booking time
     * 2. if already exists booking by request user
     * </pre>
     */
    @PostMapping
    public ResponseEntity<BookingDto> requestBooking(
            @PathVariable Long storeId,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime requestBookingTime,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

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
            @RequestParam("approve") Boolean isApprove) {

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
