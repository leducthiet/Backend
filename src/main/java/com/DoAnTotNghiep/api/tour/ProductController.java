package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Product;
import com.DoAnTotNghiep.core.tour.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public String getProduct(Model model) {
        model.addAttribute("products", productService.getAll());
        return "adminProduct";
    }

    @PostMapping("/product")
    public void createProduct(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("product") Product product) throws IOException {
        productService.createProduct(product);


        response.sendRedirect("/product");
    }

    @PostMapping("/updateProduct")
    public void updateProduct(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("product") Product product) throws IOException {
        productService.updateProduct(product);


        response.sendRedirect("/product");
    }

    @PostMapping("/deleteProduct")
    public void deleteProduct(Model model,
                               HttpServletResponse response,
                               @ModelAttribute("product") Product product) throws IOException {
        productService.deleteProduct(product);


        response.sendRedirect("/product");
    }
}
