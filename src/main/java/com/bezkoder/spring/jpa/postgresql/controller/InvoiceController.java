package com.bezkoder.spring.jpa.postgresql.controller;


import com.bezkoder.spring.jpa.postgresql.Service.InvoiceService;
import com.bezkoder.spring.jpa.postgresql.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@CrossOrigin

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/loadInoicingDataList")
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/status/{status}")
    public List<Invoice> getInvoicesByStatus(@PathVariable String status) {
        return invoiceService.getInvoicesByStatus(status);
    }

    @GetMapping("/client")
    public List<Invoice> getInvoicesByClientEmail(@RequestParam String email) {
        return invoiceService.getInvoicesByClientEmail(email);
    }

    @GetMapping("/overdue")
    public List<Invoice> getOverdueInvoices() {
        return invoiceService.getOverdueInvoices();
    }

    @GetMapping("/total-by-client")
    public BigDecimal getTotalAmountByClient(@RequestParam String email) {
        return invoiceService.getTotalAmountByClient(email);
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @PutMapping("/{id}")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}