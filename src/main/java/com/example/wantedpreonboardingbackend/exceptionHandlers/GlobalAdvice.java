package com.example.wantedpreonboardingbackend.exceptionHandlers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler
    public ErrorResponse handler(CustomException exception, HttpServletResponse response) {
        response.setStatus(exception.getHttpStatusCode());
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }
}
