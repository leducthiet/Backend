package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.paypal.PaypalService;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.entity.TourDateBooking;
import com.DoAnTotNghiep.core.tour.service.*;
import com.DoAnTotNghiep.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class TourManagerController {
    @Autowired
    TourService tourService;

    @Autowired
    TourTypeService tourTypeService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    TourImageService tourImageService;

    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    UserService userService;

    @Autowired
    TourDateBookingService tourDateBookingService;

    @Autowired
    PaypalService paypalService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    TourFeedBackService tourFeedBackService;

    @GetMapping("/tourManager")
    public String getTour(Model model) {
        model.addAttribute("tours", tourService.getAll());
        model.addAttribute("tourTypes", tourTypeService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        List<String> tourDateBookings = new ArrayList<>();
        for (Tour t : tourService.getAll()) {
            StringBuilder temp = new StringBuilder();
            for (TourDateBooking td : tourDateBookingService.getTourDateBookingByTourId(t.getId())) {
                temp.append(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
                        .format(td.getDateBooking().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())).append(",");
            }
            tourDateBookings.add(String.valueOf(temp));
        }
        model.addAttribute("tourDateBookings", tourDateBookings);
        return "adminHome";
    }
}
