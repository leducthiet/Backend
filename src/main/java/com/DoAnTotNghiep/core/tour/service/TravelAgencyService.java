package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.tour.repository.TravelAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public TravelAgency createTravelAgency(TravelAgency travelAgency) {
        return travelAgencyRepository.saveAndFlush(travelAgency);
    }

    public void updateTravelAgency(TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public void deleteTravelAgency(TravelAgency travelAgency) {
        travelAgencyRepository.deleteById(travelAgency.getId());
    }

    public Integer getPercentNumberTravelAgency() {
        Date today = new Date();
        Long presentNumber = travelAgencyRepository.getNumberTravelAgencyByMonth(today.getMonth() + 1);
        Long previousNumber = travelAgencyRepository.getNumberTravelAgencyByMonth(today.getMonth());

        if (presentNumber == null) {
            presentNumber = 0L;
        }

        if (previousNumber == null || previousNumber == 0L) {
            return 100;
        }

        return Math.toIntExact((presentNumber * 100) / previousNumber) - 100;
    }

    public TravelAgency getTravelAgencyByPaymentId(String paymentId) {
        return travelAgencyRepository.getTravelAgencyByPaymentId(paymentId);
    }
}
