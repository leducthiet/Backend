package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TourType;
import com.DoAnTotNghiep.core.tour.repository.TourTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourTypeService {
    @Autowired
    TourTypeRepository tourTypeRepository;

    public List<TourType> getAll() {
        return tourTypeRepository.findAll();
    }

    public TourType findById(Long id) {
        return tourTypeRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createTourType(TourType tourType) {
        tourTypeRepository.save(tourType);
    }

    public void updateTourType(TourType tourType) {
        tourTypeRepository.save(tourType);
    }

    public void deleteTourType(TourType tourType) {
        tourTypeRepository.deleteById(tourType.getId());
    }
}
