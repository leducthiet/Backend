package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Province;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourService;
import com.DoAnTotNghiep.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class TourBookingController {
    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TourService tourService;

    @Autowired
    UserService userService;

    @GetMapping("/tourBooking")
    public String getTourBooking(Model model) {
        model.addAttribute("tourBookings", tourBookingService.getAll());
        return "adminTourBooking";
    }

    @PostMapping("/updateTourBooking")
    public void updateTourBooking(Model model,
                                  HttpServletResponse response,
                                  @RequestParam("tourId") Long tourId,
                                  @RequestParam("usersId") Long usersId,
                                  @RequestParam("startDateString") String startDateString,
                                  @ModelAttribute("tourBooking") TourBooking tourBooking) throws IOException, ParseException {
        Tour tour = tourService.findById(tourId);
        Long totalPrice =
                (long) tourBooking.getQuantityAdult() * tour.getPriceAdult() +
                (long) tourBooking.getQuantityChild2To5() * tour.getPriceChild2To5() +
                (long) tourBooking.getQuantityChild5To11() * tour.getPriceChild5To11();

        tourBooking.setTotalPrice(totalPrice);
        tourBooking.setUsers(userService.findById(usersId));

        tourBookingService.updateTourBooking(tourBooking);

        response.sendRedirect("/tourBooking");
    }

    @PostMapping("/deleteTourBooking")
    public void deleteTourBooking(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("tourBooking") TourBooking tourBooking) throws IOException {
        tourBookingService.deleteTourBooking(tourBooking);


        response.sendRedirect("/tourBooking");
    }
}
