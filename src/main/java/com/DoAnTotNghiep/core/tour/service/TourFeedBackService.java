package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TourFeedBack;
import com.DoAnTotNghiep.core.tour.repository.TourFeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TourFeedBackService {
    @Autowired
    TourFeedBackRepository tourFeedBackRepository;

    public List<TourFeedBack> getAll() {
        return tourFeedBackRepository.findAll();
    }

    public TourFeedBack findById(Long id) {
        return tourFeedBackRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createTourFeedBack(TourFeedBack tourFeedBack) {
        tourFeedBackRepository.save(tourFeedBack);
    }

    public void updateTourFeedBack(TourFeedBack tourFeedBack) {
        tourFeedBackRepository.save(tourFeedBack);
    }

    public void deleteTourFeedBack(TourFeedBack tourFeedBack) {
        tourFeedBackRepository.deleteById(tourFeedBack.getId());
    }

    public TourFeedBack getTourFeedBackByTourBookingId(Long tourBookingId) {
        return tourFeedBackRepository.getTourFeedBackByTourBookingId(tourBookingId);
    }

    public List<TourFeedBack> getTourFeedBackByTourId(Long tourId) {
        return tourFeedBackRepository.getTourFeedBackByTourId(tourId);
    }

    public Integer getPercentFeedbackTourBooking() {
        Date today = new Date();
        Long presentNumber = tourFeedBackRepository.getNumberFeedBackByMonth(today.getMonth() + 1);
        Long previousNumber = tourFeedBackRepository.getNumberFeedBackByMonth(today.getMonth());
        return Math.toIntExact((presentNumber * 100) / previousNumber) - 100;
    }
}
