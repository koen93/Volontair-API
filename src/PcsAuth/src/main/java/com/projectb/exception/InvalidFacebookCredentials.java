package com.projectb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Provided facebook credentials are invalid.", value = HttpStatus.FORBIDDEN)
public class InvalidFacebookCredentials extends RuntimeException {

}
