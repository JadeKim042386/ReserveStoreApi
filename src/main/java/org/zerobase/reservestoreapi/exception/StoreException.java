package org.zerobase.reservestoreapi.exception;

import lombok.Getter;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;

@Getter
public class StoreException extends CustomException {
  public StoreException(ErrorCode errorCode) {
    super(errorCode);
  }

  public StoreException(ErrorCode errorCode, Exception causeException) {
    super(errorCode, causeException);
  }
}
