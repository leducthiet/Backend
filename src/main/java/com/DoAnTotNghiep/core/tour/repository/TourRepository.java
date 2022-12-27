package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query(value = "SELECT t.* FROM tour t JOIN travel_agency ta ON ta.id = t.travel_agency_id WHERE t.is_approved = 1 AND ta.expired_date >= GETDATE() AND t.province_id = ?1", nativeQuery = true)
    List<Tour> getTourApprovedByProvinceId(Long province_id);

    @Query(value = "SELECT * FROM Tour t WHERE t.travel_agency_id = ?1", nativeQuery = true)
    List<Tour> getTourByTravelAgencyId(Long travel_agency_id);

    @Query(value = "SELECT t.* FROM tour t JOIN travel_agency ta ON ta.id = t.travel_agency_id WHERE t.is_approved = 1 AND ta.expired_date >= GETDATE()", nativeQuery = true)
    List<Tour> getTourApproved();

    @Query(value = "SELECT COUNT(*) FROM tour t WHERE MONTH(t.date_create) = ?1", nativeQuery = true)
    Long countTourByMonth(int month);

    @Query(value = "SELECT t.* FROM tour t JOIN travel_agency ta ON ta.id = t.travel_agency_id WHERE t.is_approved = 1 AND ta.expired_date >= GETDATE() AND t.tour_name LIKE %?1% AND t.tour_type_id LIKE %?2% AND t.province_id LIKE %?3%", nativeQuery = true)
    List<Tour> getTourApprovedByTourNameAndTourTypeAndProvince(String tourName, String tourTypeId, String provinceId);
}
