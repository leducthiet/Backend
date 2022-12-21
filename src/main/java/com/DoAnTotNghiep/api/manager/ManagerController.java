package com.DoAnTotNghiep.api.manager;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourFeedBackService;
import com.DoAnTotNghiep.core.user.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {

    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TourFeedBackService tourFeedBackService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager")
    public String getDashboard(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("percentTourBooking",
                tourBookingService.getPercentTourBooking(users.getTravelAgency().getId()));
        model.addAttribute("percentNumberTourBooking",
                tourBookingService.getPercentNumberTourBooking(users.getTravelAgency().getId()));
        model.addAttribute("percentFeedbackTourBooking",
                tourFeedBackService.getPercentFeedbackTourBooking(users.getTravelAgency().getId()));

        return "DashboardManager";
    }


}
