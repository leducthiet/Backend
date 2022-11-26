package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TourService {
    @Autowired
    TourRepository tourRepository;

    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    public Tour createTour(Tour tour) {
        return tourRepository.saveAndFlush(tour);
    }

    public Tour findById(Long id) {
        return tourRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void updateTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void deleteTour(Tour tour) {
        tourRepository.deleteById(tour.getId());
    }

    public List<Tour> getTourByProvinceId(Long provinceId) {
        return tourRepository.getTourApprovedByProvinceId(provinceId);
    }

    public List<Tour> getTourByTravelAgencyId(Long travelAgencyId) {
        return tourRepository.getTourByTravelAgencyId(travelAgencyId);
    }

    public List<Tour> getTourApproved() {
        return tourRepository.getTourApproved();
    }

    public Integer getPercentTour() {
        Date today = new Date();
        Long presentNumber = tourRepository.countTourByMonth(today.getMonth() + 1);
        Long previousNumber = tourRepository.countTourByMonth(today.getMonth());

        if (presentNumber == null) {
            presentNumber = 0L;
        }

        if (previousNumber == null || previousNumber == 0L) {
            return 100;
        }

        return Math.toIntExact((presentNumber * 100) / previousNumber) - 100;
    }
}
