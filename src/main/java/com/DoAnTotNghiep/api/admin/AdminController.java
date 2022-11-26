package com.DoAnTotNghiep.api.admin;

import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourFeedBackService;
import com.DoAnTotNghiep.core.tour.service.TourService;
import com.DoAnTotNghiep.core.tour.service.TravelAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    TourService tourService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        model.addAttribute("percentTravelAgency", travelAgencyService.getPercentNumberTravelAgency());
        model.addAttribute("percentTour", tourService.getPercentTour());

        return "Dashboard";
    }

}
