package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourDateBookingService;
import com.DoAnTotNghiep.core.tour.service.TourService;
import com.DoAnTotNghiep.core.user.entity.Users;
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
public class TourBookingManagerController {
    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TourDateBookingService tourDateBookingService;

    @Autowired
    TourService tourService;

    @Autowired
    UserService userService;

    @PostMapping("/updateTourBookingOfTourManager")
    public void updateTourBookingOfTour(Model model,
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

        response.sendRedirect("/tourBookingOfTourManager?tourId=" + tourId);
    }

    @PostMapping("/deleteTourBookingOfTourManager")
    public void deleteTourBookingOfTour(Model model,
                                        HttpServletResponse response,
                                        @RequestParam("tourId") Long tourId,
                                        @ModelAttribute("tourBooking") TourBooking tourBooking) throws IOException {
        tourBookingService.deleteTourBooking(tourBooking);


        response.sendRedirect("/tourBookingOfTourManager?tourId=" + tourId);
    }

    @GetMapping("/tourBookingOfTourManager")
    public String getTourBookingOfTour(Model model,
                                       @RequestParam("tourId") Long tourId) {
        model.addAttribute("tourBookings", tourBookingService.getTourBookingByTourId(tourId));
        model.addAttribute("tourOfTourBooking", tourService.findById(tourId));
        return "managerTourBookingOfTour";
    }

    @GetMapping("/tourBookingManager")
    public String getTourBooking(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("tourBookings", tourBookingService.getTourBookingByTravelAgencyId(users.getTravelAgency().getId()));
        return "managerTourBooking";
    }

    @PostMapping("/updateTourBookingManager")
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

        response.sendRedirect("/tourBookingManager");
    }

    @PostMapping("/deleteTourBookingManager")
    public void deleteTourBooking(Model model,
                                  HttpServletResponse response,
                                  @ModelAttribute("tourBooking") TourBooking tourBooking) throws IOException {
        tourBookingService.deleteTourBooking(tourBooking);


        response.sendRedirect("/tourBookingManager");
    }
}
