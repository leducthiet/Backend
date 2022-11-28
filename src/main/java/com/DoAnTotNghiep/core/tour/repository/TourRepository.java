package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query(value = "SELECT * FROM Tour t WHERE t.is_approved = 1 AND t.province_id = ?1", nativeQuery = true)
    List<Tour> getTourApprovedByProvinceId(Long province_id);

    @Query(value = "SELECT * FROM Tour t WHERE t.travel_agency_id = ?1", nativeQuery = true)
    List<Tour> getTourByTravelAgencyId(Long travel_agency_id);

    @Query(value = "SELECT * FROM tour t WHERE t.is_approved = 1", nativeQuery = true)
    List<Tour> getTourApproved();

    @Query(value = "SELECT COUNT(*) FROM tour t WHERE MONTH(t.date_create) = ?1", nativeQuery = true)
    Long countTourByMonth(int month);

    @Query(value = "SELECT * FROM tour t WHERE t.is_approved = 1 AND t.tour_name LIKE %?1%", nativeQuery = true)
    List<Tour> getTourApprovedByKeyword(String keyword);

    @Query(value = "SELECT * FROM tour t WHERE t.is_approved = 1 AND t.tour_name LIKE %?1% AND t.tour_type_id = ?2", nativeQuery = true)
    List<Tour> getTourApprovedByKeywordAndTourType(String keyword, Long tourTypeId);
}
