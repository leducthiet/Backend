package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.tour.repository.TravelAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelAgencyService {
    @Autowired
    TravelAgencyRepository travelAgencyRepository;

    public List<TravelAgency> getAll() {
        return travelAgencyRepository.findAll();
    }

    public TravelAgency findById(Long id) {
        return travelAgencyRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createTravelAgency(TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public void updateTravelAgency(TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public void deleteTravelAgency(TravelAgency travelAgency) {
        travelAgencyRepository.deleteById(travelAgency.getId());
    }
}
