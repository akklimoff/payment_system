package kg.attractor.payment.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse noSuchElement(NoSuchElementException exception) {
        return ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage()).build();
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ErrorResponse illegalAccess(IllegalAccessException exception) {
        return ErrorResponse.builder(exception, HttpStatus.FORBIDDEN, exception.getMessage()).build();
    }
}
