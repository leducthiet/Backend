package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TravelAgencyRepository extends JpaRepository<TravelAgency, Long> {
    @Query(value = "SELECT COUNT(*) FROM travel_agency ta WHERE MONTH(ta.date_create) = ?1", nativeQuery = true)
    Long getNumberTravelAgencyByMonth(int month);
}
