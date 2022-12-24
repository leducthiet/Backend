package com.DoAnTotNghiep.api.export;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.repository.TourBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSVService {

    @Autowired
    TourBookingRepository tourBookingRepository;

    public ByteArrayInputStream load(Long tourDateBookingId) {
        List<TourBooking> tourBookings = tourBookingRepository.getTourBookingByTourDateBookingId(tourDateBookingId);

        ByteArrayInputStream in = CSVHelper.tourBookingsToCSV(tourBookings);
        return in;
    }
}
