package com.nexos.person.commons.exceptions.business;

import com.nexos.person.commons.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundedException extends Exception {

    public static final String NOT_FOUNDED_EXCEPTION = "Not founded Exception.";
    private ErrorCode errorCode;

    public DataNotFoundedException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DataNotFoundedException() {
        super(NOT_FOUNDED_EXCEPTION);
    }

    public DataNotFoundedException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
