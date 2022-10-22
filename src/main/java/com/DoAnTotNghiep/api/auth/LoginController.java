package com.DoAnTotNghiep.api.auth;

import com.DoAnTotNghiep.core.auth.resource.LoginRequest;
import com.DoAnTotNghiep.core.auth.resource.LoginResponse;
import com.DoAnTotNghiep.core.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password,
                               HttpServletResponse response,
                               Model model) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        try {

            LoginResponse loginResponse = loginService.login(loginRequest);
            Cookie cookie = new Cookie("jwt", loginResponse.getToken());
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            //cookie.setHttpOnly(true);
            //cookie.setSecure(true);
            response.addCookie(cookie);
        } catch (Exception e) {
            model.addAttribute("ErrorMessage", "Đăng nhập thất bại");
            return "login";
        }
        return "index";
    }
}
