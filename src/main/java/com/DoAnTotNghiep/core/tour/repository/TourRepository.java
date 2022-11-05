package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query(value = "SELECT * FROM Tour t WHERE t.province_id = ?1", nativeQuery = true)
    List<Tour> getTourByProvinceId(Long province_id);

    @Query(value = "SELECT * FROM Tour t WHERE t.travel_agency_id = ?1", nativeQuery = true)
    List<Tour> getTourByTravelAgencyId(Long travel_agency_id);
}
