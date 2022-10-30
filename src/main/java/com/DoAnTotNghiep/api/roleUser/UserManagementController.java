package com.DoAnTotNghiep.api.roleUser;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.service.*;
import com.DoAnTotNghiep.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@Controller
public class UserManagementController {
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

    @GetMapping("/viewHistoryOrder")
    public String getTourBooking(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        model.addAttribute("tourBookings", tourBookingService.getTourBookingByUserId(userImpl.getUsers().getId()));
        return "TourHistoryOrder";
    }

    @PostMapping("/updateTourBookingOrdered")
    public void updateTourBooking(Model model,
                                  HttpServletResponse response,
                                  @RequestParam("tourId") Long tourId,
                                  @RequestParam("usersId") Long usersId,
                                  @RequestParam("tourDateBookingId") Long tourDateBookingId,
                                  @ModelAttribute("tourBooking") TourBooking tourBooking) throws IOException, ParseException {
        Tour tour = tourService.findById(tourId);
        Long totalPrice =
                (long) tourBooking.getQuantityAdult() * tour.getPriceAdult() +
                        (long) tourBooking.getQuantityChild2To5() * tour.getPriceChild2To5() +
                        (long) tourBooking.getQuantityChild5To11() * tour.getPriceChild5To11();
        tourBooking.setTotalPrice(totalPrice);
        tourBooking.setUsers(userService.findById(usersId));
        tourBooking.setTourDateBooking(tourDateBookingService.findById(tourDateBookingId));

        tourBookingService.updateTourBooking(tourBooking);

        response.sendRedirect("/viewHistoryOrder");
    }

    @PostMapping("/canceledOrderTourBooking")
    public void canceledOrderTourBooking(Model model,
                                  HttpServletResponse response,
                                  @ModelAttribute("tourBooking") TourBooking tourBooking) throws IOException, ParseException {

        TourBooking tourBookingDB = tourBookingService.findById(tourBooking.getId());
        tourBookingDB.setBookingState(BookingState.CANCELLED);

        tourBookingService.updateTourBooking(tourBookingDB);

        response.sendRedirect("/viewHistoryOrder");
    }
}
