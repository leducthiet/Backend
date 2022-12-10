package com.DoAnTotNghiep.api.tour;

import com.DoAnTotNghiep.core.auth.model.UserImpl;
import com.DoAnTotNghiep.core.tour.resource.IncomeResponse;
import com.DoAnTotNghiep.core.tour.resource.TourStatisticResponse;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TourService;
import com.DoAnTotNghiep.core.user.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TourBookingApiController {

    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TourService tourService;


    @GetMapping(value = "/income")
    public ResponseEntity<?> getIncomes() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImpl userImpl = (UserImpl) userDetails;
        Users users = userImpl.getUsers();

        IncomeResponse incomeResponse = new IncomeResponse();
        incomeResponse.setIncomes(tourBookingService.getIncomeThisYear(users.getTravelAgency().getId()));
        return new ResponseEntity<>(incomeResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/tourStatistic")
    public ResponseEntity<?> getTourStatistic() {
        TourStatisticResponse tourStatisticResponse = new TourStatisticResponse();
        tourStatisticResponse.setTourStatistics(tourService.getTourThisYear());
        return new ResponseEntity<>(tourStatisticResponse, HttpStatus.OK);
    }
}
