package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.repository.TourBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TourBookingService {
    @Autowired
    TourBookingRepository tourBookingRepository;

    public List<TourBooking> getAll() {
        return tourBookingRepository.findAll();
    }

    public TourBooking findById(Long id) {
        return tourBookingRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public TourBooking createTourBooking(TourBooking tourBooking) {
        tourBooking.setDateCreate(new Date());
        return tourBookingRepository.saveAndFlush(tourBooking);
    }

    public void updateTourBooking(TourBooking tourBooking) {
        tourBookingRepository.save(tourBooking);
    }

    public void deleteTourBooking(TourBooking tourBooking) {
        tourBookingRepository.deleteById(tourBooking.getId());
    }

    public List<TourBooking> getTourBookingByUserId(Long userId) {
        return tourBookingRepository.getTourBookingByUserId(userId);
    }

    public TourBooking getTourBookingByPaymentId(String paymentId) {
        return tourBookingRepository.getTourBookingByPaymentId(paymentId);
    }

    public List<TourBooking> getTourBookingByTourId(Long tourId) {
        return tourBookingRepository.getTourBookingByTourId(tourId);
    }

    public Integer getPercentTourBooking() {
        Date today = new Date();
        Long presentIncome = tourBookingRepository.getIncomeByMonth(today.getMonth() + 1);
        Long previousIncome = tourBookingRepository.getIncomeByMonth(today.getMonth());

        if (presentIncome == null) {
            presentIncome = 0L;
        }

        if (previousIncome == null || previousIncome == 0L) {
            return 100;
        }

        return Math.toIntExact((presentIncome * 100) / previousIncome) - 100;
    }

    public Integer getPercentNumberTourBooking() {
        Date today = new Date();
        Long presentNumber = tourBookingRepository.getNumberOrderByMonth(today.getMonth() + 1);
        Long previousNumber = tourBookingRepository.getNumberOrderByMonth(today.getMonth());

        if (presentNumber == null) {
            presentNumber = 0L;
        }

        if (previousNumber == null || previousNumber == 0L) {
            return 100;
        }

        return Math.toIntExact((presentNumber * 100) / previousNumber) - 100;
    }

    public List<Long> getIncomeThisYear() {
        List<Long> incomes = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Long income = tourBookingRepository.getIncomeByMonth(i);
            if (income == null) {
                incomes.add(0L);
            } else {
                incomes.add(income);
            }
        }
        return incomes;
    }

    public List<TourBooking> getTourBookingByTravelAgencyId(Long travelAgencyId) {
        return tourBookingRepository.getTourBookingByTravelAgencyId(travelAgencyId);
    }
}
