package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Province;
import com.DoAnTotNghiep.core.tour.entity.TourFeedBack;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourDateBookingService;
import com.DoAnTotNghiep.core.tour.service.TourFeedBackService;
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
import java.util.Date;

@Controller
public class TourFeedBackController {
    @Autowired
    TourFeedBackService tourFeedBackService;

    @Autowired
    UserService userService;

    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TourDateBookingService tourDateBookingService;

    @Autowired
    TourService tourService;

    @GetMapping("/tourFeedBack")
    public String getTourFeedBack(Model model) {
        model.addAttribute("feedBacks", tourFeedBackService.getAll());
        return "adminTourFeedBack";
    }

    @GetMapping("/showFeedBack")
    public String showFeedBack(@RequestParam("tourBookingId") Long tourBookingId,
                                Model model) {
        model.addAttribute("tour",
                tourService
                        .findById(tourDateBookingService
                        .findById(tourBookingService
                        .findById(tourBookingId).getTourDateBooking()
                                .getId()).getTour().getId()));
        model.addAttribute("tourBookingId", tourBookingService.findById(tourBookingId).getId());
        model.addAttribute("usersId", tourBookingService.findById(tourBookingId).getUsers().getId());
        return "FeedBack";
    }

    @GetMapping("/showUpdateFeedBack")
    public String showUpdateFeedBack(@RequestParam("tourBookingId") Long tourBookingId,
                               Model model) {
        model.addAttribute("tour",
                tourService
                        .findById(tourDateBookingService
                                .findById(tourBookingService
                                        .findById(tourBookingId).getTourDateBooking()
                                        .getId()).getTour().getId()));
        model.addAttribute("feedBack", tourFeedBackService.getTourFeedBackByTourBookingId(tourBookingId));
        return "FeedBackUpdate";
    }

    @PostMapping("/tourFeedBack")
    public void createTourFeedBack(Model model,
                               HttpServletResponse response,
                               @RequestParam("tourBookingId") Long tourBookingId,
                               @RequestParam("usersId") Long usersId,
                               @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        tourFeedBack.setUsers(userService.findById(usersId));
        tourFeedBack.setTourBooking(tourBookingService.findById(tourBookingId));
        tourFeedBack.setCreateDate(new Date());
        tourFeedBack.setIsHidden(false);

        tourFeedBackService.createTourFeedBack(tourFeedBack);

        response.sendRedirect("/viewHistoryOrder");
    }

    @PostMapping("/updateTourFeedBack")
    public void updateTourFeedBack(Model model,
                                   HttpServletResponse response,
                                   @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setRating(tourFeedBack.getRating());
        tourFeedBackDB.setComment(tourFeedBack.getComment());

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/viewHistoryOrder");
    }

    @PostMapping("/hideTourFeedBack")
    public void hideTourFeedBack(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(true);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBack");
    }

    @PostMapping("/activeTourFeedBack")
    public void activeTourFeedBack(Model model,
                                 HttpServletResponse response,
                                 @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(false);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBack");
    }

    @PostMapping("/deleteTourFeedBack")
    public void deleteTourFeedBack(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        tourFeedBackService.deleteTourFeedBack(tourFeedBack);

        response.sendRedirect("/tourFeedBack");
    }

    @GetMapping("/tourFeedBackOfTour")
    public String tourFeedBackOfTour(@RequestParam("tourId") Long tourId,
                                     Model model) {
        model.addAttribute("feedBacks", tourFeedBackService.getTourFeedBackByTourId(tourId));
        model.addAttribute("tour", tourService.findById(tourId));
        return "adminTourFeedBackOfTour";
    }

    @PostMapping("/hideTourFeedBackOfTour")
    public void hideTourFeedBackOfTour(Model model,
                                 HttpServletResponse response,
                                 @RequestParam("tourId") Long tourId,
                                 @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(true);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBackOfTour?tourId=" + tourId);
    }

    @PostMapping("/activeTourFeedBackOfTour")
    public void activeTourFeedBackOfTour(Model model,
                                   HttpServletResponse response,
                                   @RequestParam("tourId") Long tourId,
                                   @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(false);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBackOfTour?tourId=" + tourId);
    }

    @PostMapping("/deleteTourFeedBackOfTour")
    public void deleteTourFeedBackOfTour(Model model,
                                   HttpServletResponse response,
                                   @RequestParam("tourId") Long tourId,
                                   @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        tourFeedBackService.deleteTourFeedBack(tourFeedBack);

        response.sendRedirect("/tourFeedBackOfTour?tourId=" + tourId);
    }
}
