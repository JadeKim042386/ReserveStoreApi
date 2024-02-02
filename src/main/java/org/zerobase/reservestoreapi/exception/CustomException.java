package org.zerobase.reservestoreapi.exception;

import lombok.Getter;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;

@Getter
public abstract class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        initCause(cause);
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
