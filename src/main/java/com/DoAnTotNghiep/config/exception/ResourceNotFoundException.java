package com.DoAnTotNghiep.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException
{
    private static final String MESSAGE_DEFAULT = "Tài nguyên không tồn tại";

    public ResourceNotFoundException()
    {
        super(MESSAGE_DEFAULT);
    }

    public ResourceNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ResourceNotFoundException(String message)
    {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
