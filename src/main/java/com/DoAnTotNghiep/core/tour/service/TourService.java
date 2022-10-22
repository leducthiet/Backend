package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.Tour;
import com.DoAnTotNghiep.core.tour.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return tourRepository.getTourByProvinceId(provinceId);
    }
}
