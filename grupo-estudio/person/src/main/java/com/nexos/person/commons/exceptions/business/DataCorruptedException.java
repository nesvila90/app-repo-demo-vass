package com.nexos.person.commons.exceptions.business;

import com.nexos.person.commons.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataCorruptedException extends Exception {

    private ErrorCode errorCode;

    public DataCorruptedException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DataCorruptedException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
