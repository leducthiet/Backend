package com.DoAnTotNghiep.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/tourUser");
    }
}
