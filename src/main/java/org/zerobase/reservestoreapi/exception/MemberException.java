package org.zerobase.reservestoreapi.exception;

import lombok.Getter;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;

@Getter
public class MemberException extends CustomException {
  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }

  public MemberException(ErrorCode errorCode, Exception causeException) {
    super(errorCode, causeException);
  }
}
