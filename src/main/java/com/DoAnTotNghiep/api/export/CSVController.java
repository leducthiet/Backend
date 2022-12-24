package com.DoAnTotNghiep.api.export;

import com.DoAnTotNghiep.core.tour.entity.TourDateBooking;
import com.DoAnTotNghiep.core.tour.service.TourDateBookingService;
import com.DoAnTotNghiep.core.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;

@CrossOrigin("http://localhost:8080")
@Controller
public class CSVController {

    @Autowired
    CSVService csvService;

    @Autowired
    TourService tourService;

    @Autowired
    TourDateBookingService tourDateBookingService;

    @GetMapping("/downloadCSV")
    public ResponseEntity<Resource> getFile(@RequestParam("tourDateBookingId") Long tourDateBookingId) {
        TourDateBooking tourDateBooking = tourDateBookingService.findById(tourDateBookingId);
        String filename = tourService.findById(tourDateBooking.getTour().getId()).getTourName() + "_"
                + tourDateBooking.getDateBooking() + ".csv";

        InputStreamResource file = new InputStreamResource(csvService.load(tourDateBookingId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

}
