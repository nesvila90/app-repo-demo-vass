package com.nexos.person.commons.exceptions.business.base;

import com.nexos.person.commons.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BusinessException extends Exception {

    private ErrorCode errorCode;

    public BusinessException(String message, Throwable exception, ErrorCode errorCode) {
        super(message, exception);
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
