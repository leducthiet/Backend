package com.DoAnTotNghiep.core.tour.repository;

import com.DoAnTotNghiep.core.tour.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query(value = "SELECT * FROM invoice i WHERE i.travel_agency_id = ?1", nativeQuery = true)
    List<Invoice> getInvoiceByTravelAgencyId(Long travelAgencyId);
}
