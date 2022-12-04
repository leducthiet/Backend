package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.entity.Invoice;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.tour.service.InvoiceService;
import com.DoAnTotNghiep.core.tour.service.TravelAgencyService;
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
import java.util.Calendar;
import java.util.Date;

@Controller
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    UserService userService;

    @GetMapping("/invoice")
    public String getInvoice(Model model) {
        model.addAttribute("invoices", invoiceService.getAll());
        model.addAttribute("travelAgencies", travelAgencyService.getAll());
        return "adminInvoice";
    }

    @PostMapping("/invoice")
    public void createInvoice(Model model,
                              HttpServletResponse response,
                              @ModelAttribute("invoice") Invoice invoice,
                              @RequestParam("travelAgency_id") Long travelAgencyId) throws IOException {
        invoice.setCreatedDate(new Date());
        invoice.setPrice(invoice.getMonth() * travelAgencyService.findById(travelAgencyId).getProduct().getPrice());
        invoice.setTravelAgency(travelAgencyService.findById(travelAgencyId));
        invoiceService.createInvoice(invoice);

        TravelAgency travelAgency = travelAgencyService.findById(travelAgencyId);

        Date expiredDateBefore = travelAgency.getExpiredDate();
        Calendar c = Calendar.getInstance();
        c.setTime(expiredDateBefore);
        c.add(Calendar.DATE, 30 * invoice.getMonth());
        Date expiredDateAfter = c.getTime();
        travelAgency.setExpiredDate(expiredDateAfter);
        travelAgencyService.updateTravelAgency(travelAgency);

        response.sendRedirect("/invoice");
    }

    @GetMapping("/invoiceOfTravelAgency")
    public String getInvoiceOfTravelAgency(Model model,
                                           @RequestParam("travelAgencyId") Long travelAgencyId) {
        model.addAttribute("invoices", invoiceService.getInvoiceByTravelAgencyId(travelAgencyId));
        model.addAttribute("travelAgency", travelAgencyService.findById(travelAgencyId));
        return "adminInvoiceOfTravelAgency";
    }

    @GetMapping("/invoiceOfTravelAgencyManager")
    public String getInvoiceOfTravelAgencyManager(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();
        Long travelAgencyId = userService.findById(users.getId()).getTravelAgency().getId();

        model.addAttribute("invoices", invoiceService.getInvoiceByTravelAgencyId(travelAgencyId));

        return "managerInvoice";
    }
}
