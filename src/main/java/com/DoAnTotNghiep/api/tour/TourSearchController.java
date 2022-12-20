package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.service.ProvinceService;
import com.DoAnTotNghiep.core.tour.service.TourService;
import com.DoAnTotNghiep.core.tour.service.TourTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TourSearchController {

    @Autowired
    TourService tourService;

    @Autowired
    TourTypeService tourTypeService;

    @Autowired
    ProvinceService provinceService;

    @GetMapping("/SearchOneCondition")
    public String getTourUser(Model model,
                              @RequestParam("keyword") String keyword) {
        model.addAttribute("tours", tourService.getTourApprovedByTourNameAndTourTypeAndProvince(keyword, "", ""));
        model.addAttribute("tourTypes", tourTypeService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("keyword", keyword);
        return "Search";
    }

    @GetMapping("/SearchManyCondition")
    public String searchManyCondition(Model model,
                              @RequestParam("keyword") String keyword,
                              @RequestParam("tourType_id") Long tourType_id,
                              @RequestParam("province_id") Long province_id) {
        String tourTypeId = tourType_id.toString();
        String provinceId = province_id.toString();

        if (tourTypeId.equals("-1")) {
            tourTypeId = "";
        }

        if (provinceId.equals("-1")) {
            provinceId = "";
        }

        model.addAttribute("tours", tourService.getTourApprovedByTourNameAndTourTypeAndProvince(keyword, "", ""));
        model.addAttribute("tourTypes", tourTypeService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("keyword", keyword);
        return "Search";
    }
}
