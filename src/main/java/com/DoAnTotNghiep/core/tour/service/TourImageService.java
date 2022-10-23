package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.core.tour.entity.TourImage;
import com.DoAnTotNghiep.core.tour.repository.TourImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourImageService {
    @Autowired
    TourImageRepository tourImageRepository;

    public TourImage createTourImage(TourImage tourImage) {
        return tourImageRepository.saveAndFlush(tourImage);
    }

    public List<TourImage> getTourImageByTourId(Long tourId) {
        return tourImageRepository.getTourImageByTourId(tourId);
    }
}
