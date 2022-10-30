package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourBookingRepository extends JpaRepository<TourBooking, Long> {
    @Query(value = "SELECT * FROM tour_booking t WHERE t.user_id = ?1", nativeQuery = true)
    List<TourBooking> getTourBookingByUserId(Long user_id);
}
