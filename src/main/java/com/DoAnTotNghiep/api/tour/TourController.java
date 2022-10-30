package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.tour.entity.*;
import com.DoAnTotNghiep.core.tour.service.*;
import com.DoAnTotNghiep.core.user.entity.Users;
import com.DoAnTotNghiep.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class TourController {
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

    @GetMapping("/tour")
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

    @GetMapping("/tourUser")
    public String getTourUser(Model model) {
        List<Province> provinces = provinceService.getAll();
        model.addAttribute("tours", tourService.getAll());
        model.addAttribute("tours1", tourService.getTourByProvinceId(provinces.get(0).getId()));
        model.addAttribute("tours2", tourService.getTourByProvinceId(provinces.get(1).getId()));
        model.addAttribute("tours3", tourService.getTourByProvinceId(provinces.get(2).getId()));
        model.addAttribute("tourTypes", tourTypeService.getAll());
        model.addAttribute("provinces", provinces);
        return "Home";
    }

    @PostMapping("/createTour")
    public void createTour(Model model,
                           HttpServletResponse response,
                           @ModelAttribute("tour") Tour tour,
                           @RequestParam("thumbnail") MultipartFile file,
                           @RequestParam("images") MultipartFile[] images,
                           @RequestParam("tourType_id") Long tourType_id,
                           @RequestParam("province_id") Long province_id,
                           @RequestParam("tourBookingDate") String tourBookingDate) throws IOException, ParseException {
        Path fileNameAndPath = Paths.get("src/main/resources/static/images", file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());

        tour.setThumbnailPath(file.getOriginalFilename());
        tour.setTourType(tourTypeService.findById(tourType_id));
        tour.setProvince(provinceService.findById(province_id));
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

        response.sendRedirect("/tour");
    }

    @PostMapping("/updateTour")
    public void updateTour(Model model,
                           HttpServletResponse response,
                           @ModelAttribute("tour") Tour tour,
                           @RequestParam("thumbnail") MultipartFile file,
                           @RequestParam("tourType_id") Long tourType_id,
                           @RequestParam("province_id") Long province_id,
                           @RequestParam("tourBookingDate") String tourBookingDate) throws IOException, ParseException {
        if (!file.getOriginalFilename().equals("")) {
            Path fileNameAndPath = Paths.get("src/main/resources/static/images", file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());

            tour.setThumbnailPath(file.getOriginalFilename());
        } else {
            tour.setThumbnailPath(tourService.findById(tour.getId()).getThumbnailPath());
        }

        for (TourDateBooking t : tourDateBookingService.getTourDateBookingByTourId(tour.getId())) {
            tourDateBookingService.deleteTourDateBooking(t);
        }
        String[] tourBookingDates = tourBookingDate.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (String s : tourBookingDates) {
            TourDateBooking tourDateBooking = new TourDateBooking();
            tourDateBooking.setTour(tourService.findById(tour.getId()));
            tourDateBooking.setDateBooking(dateFormat.parse(s));
            tourDateBookingService.createTourDateBooking(tourDateBooking);
        }

        tour.setTourType(tourTypeService.findById(tourType_id));
        tour.setProvince(provinceService.findById(province_id));
        tourService.updateTour(tour);
        response.sendRedirect("/tour");
    }

    @PostMapping("/deleteTour")
    public void deleteTour(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("tour") Tour tour) throws IOException {
        tourService.deleteTour(tour);


        response.sendRedirect("/tour");
    }

    @GetMapping("/tourDetail/{id}")
    public String tourDetail(@PathVariable(name = "id") Long tourId, Model model) {
        model.addAttribute("tour", tourService.findById(tourId));
        model.addAttribute("tourImages", tourImageService.getTourImageByTourId(tourId));
        model.addAttribute("tourDateBookings", tourDateBookingService.getTourDateBookingByTourId(tourId));
        return "Detail";
    }

    @GetMapping("/tourOrder/{id}")
    public String tourOrder(@PathVariable(name = "id") Long tourId, Model model) {
        model.addAttribute("tour", tourService.findById(tourId));
        model.addAttribute("tourDateBookings", tourDateBookingService.getTourDateBookingByTourId(tourId));
        return "Order";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(Model model,
                              @ModelAttribute("tourBooking") TourBooking tourBooking,
                              @RequestParam("tourDateBookingId") Long tourDateBookingId,
                              @RequestParam("tourId") Long tourId) {
        try {

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserImpl userImpl = (UserImpl) userDetails;
            tourBooking.setUsers(userService.findById(userImpl.getUsers().getId()));
            tourBooking.setTourDateBooking(tourDateBookingService.findById(tourDateBookingId));

            tourBooking.setBookingState(BookingState.PROCESSING);

            tourBookingService.createTourBooking(tourBooking);
            return "Success";
        } catch (Exception e) {
            return "Error";
        }
    }


}
