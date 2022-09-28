package com.DoAnTotNghiep.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException
{
    private static final String MESSAGE_DEFAULT = "Không có quyền truy cập";

    public ForbiddenException()
    {
        super(MESSAGE_DEFAULT);
    }

    public ForbiddenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ForbiddenException(String message)
    {
        super(message);
    }

    public ForbiddenException(Throwable cause)
    {
        super(cause);
    }
}
