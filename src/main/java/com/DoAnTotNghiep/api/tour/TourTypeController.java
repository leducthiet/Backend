package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Province;
import com.DoAnTotNghiep.core.tour.entity.TourType;
import com.DoAnTotNghiep.core.tour.service.TourTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TourTypeController {

    @Autowired
    TourTypeService tourTypeService;

    @GetMapping("/tourType")
    public String getTourType(Model model) {
        model.addAttribute("tourTypes", tourTypeService.getAll());
        return "adminTypeTour";
    }

    @PostMapping("/createTourType")
    public void createTourType(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("tourType") TourType tourType) throws IOException {
        tourTypeService.createTourType(tourType);


        response.sendRedirect("/tourType");
    }

    @PostMapping("/updateTourType")
    public void updateTourType(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("tourType") TourType tourType) throws IOException {
        tourTypeService.updateTourType(tourType);


        response.sendRedirect("/tourType");
    }

    @PostMapping("/deleteTourType")
    public void deleteTourType(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("tourType") TourType tourType) throws IOException {
        tourTypeService.deleteTourType(tourType);


        response.sendRedirect("/tourType");
    }
}
