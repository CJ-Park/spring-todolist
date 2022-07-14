package cjtodolist.springtodolist.handleError.exception;

import cjtodolist.springtodolist.handleError.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IdDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleException(IdDuplicateException exception) {
        log.error("handleException", exception);
        ErrorResponse response = new ErrorResponse(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleException(InternalServerException exception) {
        log.error("handleException", exception);
        ErrorResponse response = new ErrorResponse(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> handleException(LoginFailedException exception) {
        log.error("handleException", exception);
        ErrorResponse response = new ErrorResponse(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(NotExistsException exception) {
        log.error("handleException", exception);
        ErrorResponse response = new ErrorResponse(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

}
