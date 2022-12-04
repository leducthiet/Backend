package com.DoAnTotNghiep.api.account;

import com.DoAnTotNghiep.core.tour.service.TravelAgencyService;
import com.DoAnTotNghiep.core.user.domain.Role;
import com.DoAnTotNghiep.core.user.entity.Users;
import com.DoAnTotNghiep.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AccountController {
    @Autowired
    UserService userService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @GetMapping("/account")
    public String getAccount(Model model) {
        model.addAttribute("accounts", userService.getAll());
        model.addAttribute("travelAgencies", travelAgencyService.getAll());
        return "adminAccount";
    }

    @PostMapping("/resetPassword")
    public void resetPassword(HttpServletResponse response,
                                @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setPassword(new BCryptPasswordEncoder().encode("123123"));
        userService.updateUsers(usersDB);

        response.sendRedirect("/account");
    }

    @PostMapping("/deleteAccount")
    public void deleteAccount(HttpServletResponse response,
                              @ModelAttribute("account") Users users) throws IOException {

        userService.deleteUsers(users);

        response.sendRedirect("/account");
    }

    @PostMapping("/setRole")
    public void setRoleAdmin(HttpServletResponse response,
                             @ModelAttribute("account") Users users,
                             @RequestParam("travelAgency_id") Long travelAgencyId,
                             @RequestParam("roleSystem") String roleNumber) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setRole(Role.of(Integer.parseInt(roleNumber)));
        if (Integer.parseInt(roleNumber) == 1) {
            usersDB.setTravelAgency(travelAgencyService.findById(travelAgencyId));
        }
        userService.updateUsers(usersDB);

        response.sendRedirect("/account");
    }

    @PostMapping("/updateAccount")
    public void updateAccount(HttpServletResponse response,
                              @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setMobilePhone(users.getMobilePhone());
        usersDB.setName(users.getName());
        userService.updateUsers(usersDB);

        response.sendRedirect("/account");
    }
}
