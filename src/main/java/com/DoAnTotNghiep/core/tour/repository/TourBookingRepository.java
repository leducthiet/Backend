package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourBookingRepository extends JpaRepository<TourBooking, Long> {
    @Query(value = "SELECT * FROM tour_booking t WHERE t.user_id = ?1", nativeQuery = true)
    List<TourBooking> getTourBookingByUserId(Long user_id);

    @Query(value = "SELECT tb.* FROM tour_booking tb JOIN tour_date_booking td ON td.id = tb.tour_date_booking_id WHERE td.tour_id = ?1", nativeQuery = true)
    List<TourBooking> getTourBookingByTourId(Long tour_id);

    @Query(value = "SELECT * FROM tour_booking t WHERE t.payment_id = ?1", nativeQuery = true)
    TourBooking getTourBookingByPaymentId(String payment_id);

    @Query(value = "SELECT SUM(tb.total_price) FROM tour_booking tb WHERE tb.booking_state = 2 AND MONTH(tb.date_create) = ?1", nativeQuery = true)
    Long getIncomeByMonth(int month);

    @Query(value = "SELECT COUNT(*) FROM tour_booking tb WHERE MONTH(tb.date_create) = ?1", nativeQuery = true)
    Long getNumberOrderByMonth(int month);
}
