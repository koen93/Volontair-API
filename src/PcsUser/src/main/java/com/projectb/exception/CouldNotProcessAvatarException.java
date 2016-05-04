package com.projectb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotProcessAvatarException extends RuntimeException {
    public CouldNotProcessAvatarException() {
        super();
    }

    public CouldNotProcessAvatarException(Throwable cause) {
        super(cause);
    }
}
