package com.DoAnTotNghiep.api.manager;

import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {

    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TourFeedBackService tourFeedBackService;

    @GetMapping("/manager")
    public String getDashboard(Model model) {
        model.addAttribute("percentTourBooking", tourBookingService.getPercentTourBooking());
        model.addAttribute("percentNumberTourBooking", tourBookingService.getPercentNumberTourBooking());
        model.addAttribute("percentFeedbackTourBooking", tourFeedBackService.getPercentFeedbackTourBooking());

        return "DashboardManager";
    }


}
