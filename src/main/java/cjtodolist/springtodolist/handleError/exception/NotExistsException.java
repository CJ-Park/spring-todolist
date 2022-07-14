package cjtodolist.springtodolist.handleError.exception;

import cjtodolist.springtodolist.handleError.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotExistsException extends RuntimeException {

    private ErrorCode errorCode;

    public NotExistsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
