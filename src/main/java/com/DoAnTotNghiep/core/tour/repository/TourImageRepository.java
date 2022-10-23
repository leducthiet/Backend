package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourImageRepository extends JpaRepository<TourImage, Long> {
    @Query(value = "SELECT * FROM tour_image t WHERE t.tour_id = ?1", nativeQuery = true)
    List<TourImage> getTourImageByTourId(Long tour_id);
}
