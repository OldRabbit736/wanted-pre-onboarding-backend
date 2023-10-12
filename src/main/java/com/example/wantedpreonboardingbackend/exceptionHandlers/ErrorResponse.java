package com.example.wantedpreonboardingbackend.exceptionHandlers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String errorMessage;
}
