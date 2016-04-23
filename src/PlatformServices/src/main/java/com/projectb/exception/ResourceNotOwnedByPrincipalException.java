package com.projectb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Resource is not owned by principal.")
public class ResourceNotOwnedByPrincipalException extends RuntimeException {
}
