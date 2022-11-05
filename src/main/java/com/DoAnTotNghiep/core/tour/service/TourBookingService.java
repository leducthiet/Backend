package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.repository.TourBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
