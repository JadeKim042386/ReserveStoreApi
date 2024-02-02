package org.zerobase.reservestoreapi.exception;

import lombok.Getter;
import org.zerobase.reservestoreapi.dto.response.ExceptionResponse;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;

@Getter
public class ValidatedException extends CustomException {
  private final ExceptionResponse exceptionResponse;

  public ValidatedException(ErrorCode errorCode, ExceptionResponse exceptionResponse) {
    super(errorCode);
    this.exceptionResponse = exceptionResponse;
  }

  public ValidatedException(
      ErrorCode errorCode, Throwable cause, ExceptionResponse exceptionResponse) {
    super(errorCode, cause);
    this.exceptionResponse = exceptionResponse;
  }
}
