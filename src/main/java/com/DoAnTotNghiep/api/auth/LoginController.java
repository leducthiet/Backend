package com.DoAnTotNghiep.api.auth;

import com.DoAnTotNghiep.core.auth.resource.LoginRequest;
import com.DoAnTotNghiep.core.auth.resource.LoginResponse;
import com.DoAnTotNghiep.core.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = loginService.login(loginRequest);
        Cookie cookie = new Cookie("jwt", loginResponse.getToken());
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        //cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponse);
    }
}
