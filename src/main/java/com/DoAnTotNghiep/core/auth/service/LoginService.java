package com.DoAnTotNghiep.core.auth.service;

import com.DoAnTotNghiep.config.security.JwtTokenProvider;
import com.DoAnTotNghiep.core.auth.resource.LoginRequest;
import com.DoAnTotNghiep.core.auth.resource.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(tokenProvider.generateToken(loginRequest.getUsername()));
        return loginResponse;
    }
}
