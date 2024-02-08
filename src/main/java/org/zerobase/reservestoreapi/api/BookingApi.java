package org.zerobase.reservestoreapi.api;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.exception.BookingException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/bookings")
public class BookingApi {
    private final BookingService bookingService;

    /**
     * Retrieve booking info for a specific date for a specific store.
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/bookings?approve=true&date=2024-02-03T00:00:00
     * - /api/v1/stores/1/bookings?date=2024-02-03T00:00:00
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "content": List<BookingDto>,
     *     "number": 0,
     *     "size": 10,
     *     "sort": [
     *         {
     *             "direction": "ASC",
     *             "property": "createdAt",
     *             "ignoreCase": false,
     *             "nullHandling": "NATIVE",
     *             "descending": false,
     *             "ascending": true
     *         }
     *     ],
     *     "totalElements": 0,
     *     "totalPages": 0,
     *     "first": true,
     *     "last": true
     * }
     * </pre>
     */
    @GetMapping
    public ResponseEntity<PagedResponse<BookingDto>> searchBookingsByDate(
            @PathVariable Long storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @QuerydslPredicate(root = Booking.class) Predicate predicate) {

        return ResponseEntity.ok(bookingService.searchBookingsByDate(storeId, predicate, pageable));
    }

    /**
     * Request booking for a specific time and a specific store
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/bookings?time=2024-02-05T09:00:00
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "approve": false,
     *     "phone": "01011111111",
     *     "createdAt": 2024-02-04T09:00:00,
     *     "createdBy": "admin"
     * }
     * </pre>
     *
     * @throws BookingException if not request booking at one day before ({@code ErrorCode.NOT_POSSIBLE_BOOKING})
     */
    @Operation(summary = "Request booking for a specific time and a specific store")
    @PostMapping
    public ResponseEntity<BookingDto> requestBooking(
            @PathVariable Long storeId,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime requestBookingTime,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        // request booking at least one day before
        if (!LocalDate.now().isBefore(requestBookingTime.toLocalDate())) {
            throw new BookingException(ErrorCode.NOT_POSSIBLE_BOOKING);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        bookingService.requestBooking(
                                memberPrincipal.phone(),
                                memberPrincipal.username(),
                                storeId,
                                requestBookingTime));
    }

    /**
     * store can deny or approve the booking. if the requested store and the booking store do not
     * match, throw exception.
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/bookings/1?approve=true
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "message": "you're successfully confirm."
     * }
     * </pre>
     */
    @Operation(summary = "store can deny or approve the booking")
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
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1/bookings
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "message": "you're successfully confirm."
     * }
     * </pre>
     */
    @Operation(summary = "when a user visits the store, can request a visit check")
    @DeleteMapping
    public ResponseEntity<ApiResponse> checkVisit(
            @PathVariable Long storeId, @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        bookingService.checkVisit(memberPrincipal.username(), storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of("you're successfully check visit."));
    }
}
