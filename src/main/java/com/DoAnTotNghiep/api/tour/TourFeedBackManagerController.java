package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.entity.TourFeedBack;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourDateBookingService;
import com.DoAnTotNghiep.core.tour.service.TourFeedBackService;
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

@Controller
public class TourFeedBackManagerController {

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

    @GetMapping("/tourFeedBackOfTourManager")
    public String tourFeedBackOfTour(@RequestParam("tourId") Long tourId,
                                     Model model) {
        model.addAttribute("feedBacks", tourFeedBackService.getTourFeedBackByTourId(tourId));
        model.addAttribute("tour", tourService.findById(tourId));
        return "managerTourFeedBackOfTour";
    }

    @PostMapping("/hideTourFeedBackOfTourManager")
    public void hideTourFeedBackOfTour(Model model,
                                       HttpServletResponse response,
                                       @RequestParam("tourId") Long tourId,
                                       @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(true);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBackOfTourManager?tourId=" + tourId);
    }

    @PostMapping("/activeTourFeedBackOfTourManager")
    public void activeTourFeedBackOfTour(Model model,
                                         HttpServletResponse response,
                                         @RequestParam("tourId") Long tourId,
                                         @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(false);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBackOfTourManager?tourId=" + tourId);
    }

    @PostMapping("/deleteTourFeedBackOfTourManager")
    public void deleteTourFeedBackOfTour(Model model,
                                         HttpServletResponse response,
                                         @RequestParam("tourId") Long tourId,
                                         @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        tourFeedBackService.deleteTourFeedBack(tourFeedBack);

        response.sendRedirect("/tourFeedBackOfTourManager?tourId=" + tourId);
    }

    // feedback

    @GetMapping("/tourFeedBackManager")
    public String getTourFeedBack(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("feedBacks", tourFeedBackService.getTourFeedBackByTravelAgencyId(users.getTravelAgency().getId()));
        return "managerTourFeedBack";
    }

    @PostMapping("/hideTourFeedBackManager")
    public void hideTourFeedBack(Model model,
                                 HttpServletResponse response,
                                 @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(true);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBackManager");
    }

    @PostMapping("/activeTourFeedBackManager")
    public void activeTourFeedBack(Model model,
                                   HttpServletResponse response,
                                   @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        TourFeedBack tourFeedBackDB = tourFeedBackService.findById(tourFeedBack.getId());
        tourFeedBackDB.setIsHidden(false);

        tourFeedBackService.updateTourFeedBack(tourFeedBackDB);

        response.sendRedirect("/tourFeedBackManager");
    }

    @PostMapping("/deleteTourFeedBackManager")
    public void deleteTourFeedBack(Model model,
                                   HttpServletResponse response,
                                   @ModelAttribute("feedBack") TourFeedBack tourFeedBack) throws IOException {
        tourFeedBackService.deleteTourFeedBack(tourFeedBack);

        response.sendRedirect("/tourFeedBackManager");
    }
}
