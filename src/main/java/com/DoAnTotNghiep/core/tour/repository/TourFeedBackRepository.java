package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TourFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourFeedBackRepository extends JpaRepository<TourFeedBack, Long> {
    @Query(value = "SELECT * FROM tour_feed_back t WHERE t.tour_booking_id = ?1", nativeQuery = true)
    TourFeedBack getTourFeedBackByTourBookingId(Long tour_booking_id);

    @Query(value = "SELECT * FROM tour_feed_back tf JOIN tour_booking tb ON tb.id = tf.tour_booking_id JOIN tour_date_booking td ON td.id = tb.tour_date_booking_id WHERE td.tour_id = ?1", nativeQuery = true)
    List<TourFeedBack> getTourFeedBackByTourId(Long tour_id);

    @Query(value = "SELECT ISNULL(SUM(rating), 0) FROM tour_feed_back tf JOIN users u ON u.id = tf.user_id WHERE MONTH(tf.create_date) = ?1 AND u.travel_agency_id = ?2", nativeQuery = true)
    Long getNumberFeedBackByMonth(int month, Long travelAgencyId);

    @Query(value = "SELECT tf.* FROM tour_feed_back tf JOIN tour_booking tb ON tf.tour_booking_id = tb.id JOIN tour_date_booking td ON td.id = tb.tour_date_booking_id JOIN tour t ON t.id = td.tour_id WHERE t.travel_agency_id = ?1", nativeQuery = true)
    List<TourFeedBack> getTourFeedBackByTravelAgencyId(Long travel_agency_id);
}
