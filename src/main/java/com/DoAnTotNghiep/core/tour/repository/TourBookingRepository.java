package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourBookingRepository extends JpaRepository<TourBooking, Long> {
//    @Query(value = "select sum(quantity_adult) + sum(quantity_child2to5) + sum(quantity_child5to11) from tour_booking t where t.tour_id = ?1", nativeQuery = true)
//    int countCurrentPeopleByTourId(Long tour_id);
}
