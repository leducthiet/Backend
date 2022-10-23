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

    public void createTourBooking(TourBooking tourBooking) {
        tourBookingRepository.save(tourBooking);
    }

    public void updateTourBooking(TourBooking tourBooking) {
        tourBookingRepository.save(tourBooking);
    }

    public void deleteTourBooking(TourBooking tourBooking) {
        tourBookingRepository.deleteById(tourBooking.getId());
    }
}
