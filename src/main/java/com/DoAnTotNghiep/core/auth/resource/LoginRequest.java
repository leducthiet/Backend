package com.DoAnTotNghiep.core.auth.resource;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    //@Size(min = 4, max = 100)
    @NotNull
    private String username;

    //@Size(min = 8, max = 16)
    @NotNull
    private String password;
}
