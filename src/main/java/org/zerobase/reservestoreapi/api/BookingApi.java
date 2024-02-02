package org.zerobase.reservestoreapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.dto.MemberPrincipal;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.service.BookingService;
import org.zerobase.reservestoreapi.service.MemberService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingApi {
  private final BookingService bookingService;
  private final MemberService memberService;

  /**
   * Look up booking info for a specific date for a specific store. Returns Page based on SSR(Server
   * Side Rendering). Therefore, the return type may change in the future.
   */
  @GetMapping("/{storeId}")
  public ResponseEntity<?> searchBookingsByDate(
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
      @PathVariable Long storeId,
      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate queryDate) {
    return ResponseEntity.ok(
        bookingService.searchBookingsByDate(queryDate.atTime(0, 0, 0), storeId, pageable));
  }

  /**
   * Request booking for a specific time for a specific store.
   *
   * <p>throw an exception followed: 1. if requested user wasn't sign up 2. if already exists
   * booking at request booking time 3. if already exists booking by request user
   */
  @PostMapping("/{storeId}")
  public ResponseEntity<?> requestBooking(
      @PathVariable Long storeId,
      @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime requestBookingTime,
      @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
    if (memberService.isExistsUsername(memberPrincipal.getUsername())) {
      // TODO: handling exception
      throw new RuntimeException("already exists username");
    }
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            bookingService.requestBooking(memberPrincipal.username(), storeId, requestBookingTime));
  }

  /**
   * store can deny or approve the booking. if the requested store and the booking store do not
   * match, throw exception.
   */
  @PutMapping("/{bookingId}")
  public ResponseEntity<?> confirmBooking(
      @PathVariable Long bookingId,
      @RequestParam Boolean isApprove,
      @RequestParam String storeName) {
    bookingService.confirmBooking(bookingId, isApprove, storeName);
    return ResponseEntity.ok(ApiResponse.of("you're successfully confirm."));
  }

  /**
   * when a user visits the store, can request a visit check. if the user wasn't arrive 10 minutes
   * earlier than the booking time, throw exception.
   */
  @DeleteMapping("/{storeId}")
  public ResponseEntity<?> checkVisit(
      @PathVariable Long storeId, @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
    bookingService.checkVisit(memberPrincipal.username(), storeId);
    return ResponseEntity.ok(ApiResponse.of("you're successfully check visit."));
  }
}
