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
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
                               HttpSession session,
                               Model model) throws IOException {
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
//            session.setAttribute("username", username);
            response.addCookie(cookie);
            if (username.contains("admin")) {
                response.sendRedirect("/admin");
            } else if (username.contains("manager")) {
                response.sendRedirect("/manager");
            } else {
                response.sendRedirect("/");
            }
            model.addAttribute("ErrorMessage", "Đăng nhập thất bại");
            return "forward:/";
        } catch (Exception e) {
            model.addAttribute("ErrorMessage", "Đăng nhập thất bại");
            return "login";
        }

    }

    @GetMapping("/logout2")
    public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
//        Cookie cookie = WebUtils.getCookie(request, "jwt");
//        if (cookie != null) cookie.setMaxAge(0);
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        session.invalidate();
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
