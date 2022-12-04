package com.DoAnTotNghiep.api.profile;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.tour.service.ProductService;
import com.DoAnTotNghiep.core.tour.service.TravelAgencyService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class ProfileController {
    @Autowired
    UserService userService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    ProductService productService;

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

    @GetMapping("/managerProfile")
    public String getManagerProfile(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("travelAgency", travelAgencyService.findById(users.getTravelAgency().getId()));
        model.addAttribute("products", productService.getAll());
        model.addAttribute("account", userService.findById(users.getId()));

        return "managerProfile";
    }

    @PostMapping("/updateTravelAgencyProfile")
    public void updateTravelAgency(Model model,
                                   HttpServletResponse response,
                                   @RequestParam("thumbnail") MultipartFile file,
                                   @RequestParam("product_id") Long product_id,
                                   @ModelAttribute("travelAgency") TravelAgency travelAgency) throws IOException {
        if (!Objects.equals(file.getOriginalFilename(), "")) {
            Path fileNameAndPath = Paths.get("src/main/resources/static/images", file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());

            travelAgency.setBusinessLicense(file.getOriginalFilename());
        } else {
            travelAgency.setBusinessLicense(travelAgencyService.findById(travelAgency.getId()).getBusinessLicense());
        }

        TravelAgency travelAgencyDB = travelAgencyService.findById(travelAgency.getId());

        travelAgency.setProduct(productService.findById(product_id));
        travelAgency.setExpiredDate(travelAgencyDB.getExpiredDate());
        travelAgency.setDateCreate(travelAgencyDB.getDateCreate());
        travelAgencyService.updateTravelAgency(travelAgency);

        response.sendRedirect("/managerProfile");
    }

    @PostMapping("/changePasswordManager")
    public void changePasswordManager(HttpServletResponse response,
                               @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        userService.updateUsers(usersDB);

        response.sendRedirect("/managerProfile");
    }

    @GetMapping("/adminProfile")
    public String getAdminProfile(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("account", userService.findById(users.getId()));

        return "adminProfile";
    }

    @PostMapping("/updateAccountProfileAdmin")
    public void updateAccountAdmin(HttpServletResponse response,
                              @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setMobilePhone(users.getMobilePhone());
        usersDB.setName(users.getName());
        userService.updateUsers(usersDB);

        response.sendRedirect("/adminProfile");
    }

    @PostMapping("/changePasswordAdmin")
    public void changePasswordAdmin(HttpServletResponse response,
                               @ModelAttribute("account") Users users) throws IOException {
        Users usersDB = userService.findById(users.getId());
        usersDB.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        userService.updateUsers(usersDB);

        response.sendRedirect("/adminProfile");
    }
}
