package cjtodolist.springtodolist.handleError.exception;

import cjtodolist.springtodolist.handleError.error.ErrorCode;
import lombok.Getter;

@Getter
public class InternalServerException extends RuntimeException {
    private ErrorCode errorCode;

    public InternalServerException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
