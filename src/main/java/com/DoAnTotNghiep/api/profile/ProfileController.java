package com.DoAnTotNghiep.api.profile;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.user.entity.Users;
import com.DoAnTotNghiep.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String index(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("account", userService.findById(users.getId()));

        return "Profile";
    }

    @PostMapping("/updateAccountProfile")
    public void updateAccount(HttpServletResponse response,
                              @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setMobilePhone(users.getMobilePhone());
        usersDB.setName(users.getName());
        userService.updateUsers(usersDB);

        response.sendRedirect("/profile");
    }

    @PostMapping("/changePassword")
    public void changePassword(HttpServletResponse response,
                              @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        userService.updateUsers(usersDB);

        response.sendRedirect("/profile");
    }
}
