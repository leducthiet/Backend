package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TourDateBooking;
import com.DoAnTotNghiep.core.tour.repository.TourDateBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourDateBookingService {
    @Autowired
    TourDateBookingRepository tourDateBookingRepository;

    public List<TourDateBooking> getAll() {
        return tourDateBookingRepository.findAll();
    }

    public TourDateBooking findById(Long id) {
        return tourDateBookingRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createTourDateBooking(TourDateBooking tourDateBooking) {
        tourDateBookingRepository.save(tourDateBooking);
    }

    public void updateTourDateBooking(TourDateBooking tourDateBooking) {
        tourDateBookingRepository.save(tourDateBooking);
    }

    public void deleteTourDateBooking(TourDateBooking tourDateBooking) {
        tourDateBookingRepository.deleteById(tourDateBooking.getId());
    }

    public List<TourDateBooking> getTourDateBookingByTourId(Long tourId) {
        return tourDateBookingRepository.getTourDateBookingByTourId(tourId);
    }
}
