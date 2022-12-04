package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.Invoice;
import com.DoAnTotNghiep.core.tour.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoiceByTravelAgencyId(Long travelAgencyId) {
        return invoiceRepository.getInvoiceByTravelAgencyId(travelAgencyId);
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Invoice invoice) {
        invoiceRepository.deleteById(invoice.getId());
    }
}
