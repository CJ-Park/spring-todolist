package cjtodolist.springtodolist.handleError.exception;

import cjtodolist.springtodolist.handleError.error.ErrorCode;
import lombok.Getter;

@Getter
public class LoginFailedException extends RuntimeException {
    private ErrorCode errorCode;

    public LoginFailedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
