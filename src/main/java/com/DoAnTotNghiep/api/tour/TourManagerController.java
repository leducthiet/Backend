package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.paypal.PaypalService;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.entity.TourDateBooking;
import com.DoAnTotNghiep.core.tour.entity.TourImage;
import com.DoAnTotNghiep.core.tour.service.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        model.addAttribute("travelAgencyId", users.getTravelAgency().getId());
        model.addAttribute("tours", tourService.getTourByTravelAgencyId(users.getTravelAgency().getId()));
        model.addAttribute("tourTypes", tourTypeService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        List<String> tourDateBookings = new ArrayList<>();
        for (Tour t : tourService.getTourByTravelAgencyId(users.getTravelAgency().getId())) {
            StringBuilder temp = new StringBuilder();
            for (TourDateBooking td : tourDateBookingService.getTourDateBookingByTourId(t.getId())) {
                temp.append(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
                        .format(td.getDateBooking().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())).append(",");
            }
            tourDateBookings.add(String.valueOf(temp));
        }
        model.addAttribute("tourDateBookings", tourDateBookings);
        return "managerTour";
    }

    @PostMapping("/createTourManager")
    public void createTour(Model model,
                           HttpServletResponse response,
                           @ModelAttribute("tour") Tour tour,
                           @RequestParam("thumbnail") MultipartFile file,
                           @RequestParam("images") MultipartFile[] images,
                           @RequestParam("travelAgencyId") Long travelAgencyId,
                           @RequestParam("tourType_id") Long tourType_id,
                           @RequestParam("province_id") Long province_id,
                           @RequestParam("tourBookingDate") String tourBookingDate) throws IOException, ParseException {
        Path fileNameAndPath = Paths.get("src/main/resources/static/images", file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());

        tour.setThumbnailPath(file.getOriginalFilename());
        tour.setTourType(tourTypeService.findById(tourType_id));
        tour.setProvince(provinceService.findById(province_id));
        tour.setTravelAgency(travelAgencyService.findById(travelAgencyId));
        Tour tourCreated = tourService.createTour(tour);

        String[] tourBookingDates = tourBookingDate.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (String s : tourBookingDates) {
            TourDateBooking tourDateBooking = new TourDateBooking();
            tourDateBooking.setTour(tourCreated);
            tourDateBooking.setDateBooking(dateFormat.parse(s));
            tourDateBookingService.createTourDateBooking(tourDateBooking);
        }

        if (images.length != 0) {
            for (MultipartFile image : images) {
                if (!image.getOriginalFilename().equals("")) {
                    TourImage tourImage = new TourImage();
                    tourImage.setImagePath(image.getOriginalFilename());
                    tourImage.setTour(tourCreated);
                    tourImageService.createTourImage(tourImage);

                    Path fileNameAndPathImage = Paths.get("src/main/resources/static/images", image.getOriginalFilename());
                    Files.write(fileNameAndPathImage, image.getBytes());
                }

            }
        }

        response.sendRedirect("/tourManager");
    }
}
