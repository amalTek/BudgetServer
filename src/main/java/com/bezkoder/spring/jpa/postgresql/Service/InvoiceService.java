package com.bezkoder.spring.jpa.postgresql.Service;


import com.bezkoder.spring.jpa.postgresql.model.Invoice;
import com.bezkoder.spring.jpa.postgresql.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    public List<Invoice> getInvoicesByClientEmail(String email) {
        return invoiceRepository.findByClientEmail(email);
    }

    public List<Invoice> getOverdueInvoices() {
        return invoiceRepository.findOverdueInvoices();
    }

    public BigDecimal getTotalAmountByClient(String email) {
        return invoiceRepository.calculateTotalAmountByClient(email);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setClientEmail(updatedInvoice.getClientEmail());
            invoice.setStatus(updatedInvoice.getStatus());
            invoice.setTotalAmount(updatedInvoice.getTotalAmount());
            invoice.setDueDate(updatedInvoice.getDueDate());
            return invoiceRepository.save(invoice);
        }).orElse(null);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
