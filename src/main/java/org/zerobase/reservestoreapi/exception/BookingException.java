package org.zerobase.reservestoreapi.exception;

import lombok.Getter;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;

@Getter
public class BookingException extends CustomException {
  public BookingException(ErrorCode errorCode) {
    super(errorCode);
  }

  public BookingException(ErrorCode errorCode, Exception causeException) {
    super(errorCode, causeException);
  }
}
