package com.example.wantedpreonboardingbackend.exceptionHandlers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public String getCode() {
        return this.exceptionCode.getCode();
    }

    public String getMessage() {
        return this.exceptionCode.getMessage();
    }

    public int getHttpStatusCode() {
        return this.exceptionCode.getHttpStatusCode();
    }

    @Getter
    public enum ExceptionCode {
        E1000("존재하지 않는 리소스입니다.", 404),
        E1001("이미 해당 공고에 지원했습니다. 중복해서 지원할 수 없습니다.", 409)
        ;

        private final String code;
        private final String message;
        private final int httpStatusCode;

        ExceptionCode(String message, int httpStatusCode) {
            this.code = this.name();
            this.message = message;
            this.httpStatusCode = httpStatusCode;
        }
    }
}
