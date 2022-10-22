package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Province;
import com.DoAnTotNghiep.core.tour.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping("/province")
    public String getProvince(Model model) {
        model.addAttribute("provinces", provinceService.getAll());
        return "adminProvince";
    }

    @PostMapping("/province")
    public void createProvince(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("province") Province province) throws IOException {
        provinceService.createProvince(province);


        response.sendRedirect("/province");
    }

    @PostMapping("/updateProvince")
    public void updateProvince(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("province") Province province) throws IOException {
        provinceService.updateProvince(province);


        response.sendRedirect("/province");
    }

    @PostMapping("/deleteProvince")
    public void deleteProvince(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("province") Province province) throws IOException {
        provinceService.deleteProvince(province);


        response.sendRedirect("/province");
    }
}
