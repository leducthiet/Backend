package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.tour.entity.Contact;
import com.DoAnTotNghiep.core.tour.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class ContactController {
    @Autowired
    ContactService contactService;

    @GetMapping("/adminContact")
    public String getContact(Model model) {
        model.addAttribute("contacts", contactService.getAll());
        return "adminContact";
    }

    @PostMapping("/adminContact")
    public String createContact(Model model,
                                @ModelAttribute("contact") Contact contact) {
        contact.setCreatedDate(new Date());
        contactService.createContact(contact);
        model.addAttribute("message", "Gửi thành công");
        return "ContactUs";
    }

    @PostMapping("/deleteContact")
    public void deleteContact(HttpServletResponse response,
                                @ModelAttribute("contact") Contact contact) throws IOException {
        contactService.deleteContact(contact);
        response.sendRedirect("/adminContact");
    }
}
