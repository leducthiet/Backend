package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Province;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.entity.TourDateBooking;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.tour.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class TravelAgencyController {
    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    TourService tourService;

    @Autowired
    TourTypeService tourTypeService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    TourDateBookingService tourDateBookingService;

    @GetMapping("/travelAgency")
    public String getTravelAgency(Model model) {
        model.addAttribute("travelAgencies", travelAgencyService.getAll());
        return "adminTravelAgency";
    }

    @PostMapping("/travelAgency")
    public void createTravelAgency(Model model,
                               HttpServletResponse response,
                               @RequestParam("thumbnail") MultipartFile file,
                               @ModelAttribute("province") TravelAgency travelAgency) throws IOException {
        Path fileNameAndPath = Paths.get("src/main/resources/static/images", file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        travelAgency.setBusinessLicense(file.getOriginalFilename());

        travelAgency.setDateCreate(new Date());
        travelAgencyService.createTravelAgency(travelAgency);
        response.sendRedirect("/travelAgency");
    }

    @PostMapping("/updateTravelAgency")
    public void updateTravelAgency(Model model,
                               HttpServletResponse response,
                               @RequestParam("thumbnail") MultipartFile file,
                               @ModelAttribute("travelAgency") TravelAgency travelAgency) throws IOException {
        if (!Objects.equals(file.getOriginalFilename(), "")) {
            Path fileNameAndPath = Paths.get("src/main/resources/static/images", file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());

            travelAgency.setBusinessLicense(file.getOriginalFilename());
        } else {
            travelAgency.setBusinessLicense(travelAgencyService.findById(travelAgency.getId()).getBusinessLicense());
        }

        travelAgency.setDateCreate(travelAgencyService.findById(travelAgency.getId()).getDateCreate());
        travelAgencyService.updateTravelAgency(travelAgency);

        response.sendRedirect("/travelAgency");
    }

    @PostMapping("/deleteTravelAgency")
    public void deleteTravelAgency(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("travelAgency") TravelAgency travelAgency) throws IOException {
        travelAgencyService.deleteTravelAgency(travelAgency);


        response.sendRedirect("/travelAgency");
    }

    @GetMapping("/tourOfTravelAgency")
    public String tourOfTravelAgency(@RequestParam("travelAgencyId") Long travelAgencyId,
                                     Model model) {
        model.addAttribute("travelAgency", travelAgencyService.findById(travelAgencyId));

        model.addAttribute("tours", tourService.getTourByTravelAgencyId(travelAgencyId));
        model.addAttribute("tourTypes", tourTypeService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        List<String> tourDateBookings = new ArrayList<>();
        for (Tour t : tourService.getTourByTravelAgencyId(travelAgencyId)) {
            StringBuilder temp = new StringBuilder();
            for (TourDateBooking td : tourDateBookingService.getTourDateBookingByTourId(t.getId())) {
                temp.append(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
                        .format(td.getDateBooking().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())).append(",");
            }
            tourDateBookings.add(String.valueOf(temp));
        }
        model.addAttribute("tourDateBookings", tourDateBookings);

        return "adminTourOfTravelAgency";
    }
}
