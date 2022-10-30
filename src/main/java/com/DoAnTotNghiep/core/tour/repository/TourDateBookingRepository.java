package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TourDateBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourDateBookingRepository extends JpaRepository<TourDateBooking, Long> {
    @Query(value = "SELECT * FROM tour_date_booking t WHERE t.tour_id = ?1", nativeQuery = true)
    List<TourDateBooking> getTourDateBookingByTourId(Long tour_id);
}
