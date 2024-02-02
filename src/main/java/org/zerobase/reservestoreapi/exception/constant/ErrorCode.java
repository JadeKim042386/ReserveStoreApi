package org.zerobase.reservestoreapi.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Common
  INTERNAL_SERVER_ERROR_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "occurs internal server error"),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "it's bad request"),
  FAILED_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, "failed save"),
  FAILED_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "failed delete"),
  CONFLICT_DELETE(HttpStatus.CONFLICT, "Optimistic Lock conflict when delete"),
  CONFLICT_SAVE(HttpStatus.CONFLICT, "Optimistic Lock conflict when save"),
  NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "not found entity"),
  ALREADY_EXISTS_USERNAME_OR_NICKNAME(
      HttpStatus.BAD_REQUEST, "already exists nickname or username"),
  ALREADY_EXISTS_STORE_NAME(HttpStatus.BAD_REQUEST, "already exists store name"),
  // Booking
  ALREADY_EXISTS_BOOKING(HttpStatus.BAD_REQUEST, "already exists booking"),
  LATE_VISIT(HttpStatus.BAD_REQUEST, "you're late. you should be visit before 10 minutes."),
  // store
  NOT_MATCH_STORE(HttpStatus.BAD_REQUEST, "requested store and booking store do not match!"),
  // sign up
  INVALID_INTERVAL_TIME(
      HttpStatus.BAD_REQUEST, "intervalTime must not be greater than lastTime - startTime"),
  ;

  private HttpStatus status;
  private String message;
}
