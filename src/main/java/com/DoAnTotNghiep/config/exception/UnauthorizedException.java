package com.DoAnTotNghiep.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException
{
    private static final String MESSAGE_DEFAULT = "Người dùng truy cập không hợp lệ";

    public UnauthorizedException()
    {
        super(MESSAGE_DEFAULT);
    }

    public UnauthorizedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnauthorizedException(String message)
    {
        super(message);
    }

    public UnauthorizedException(Throwable cause)
    {
        super(cause);
    }
}