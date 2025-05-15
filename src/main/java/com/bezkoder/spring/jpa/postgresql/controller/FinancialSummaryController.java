package com.bezkoder.spring.jpa.postgresql.controller;

import com.bezkoder.spring.jpa.postgresql.Service.FinancialSummaryService;
import com.bezkoder.spring.jpa.postgresql.model.FinancialSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin()
@RestController
@RequestMapping("/api/financialSummary")
public class FinancialSummaryController {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    // Create a new financial summary
    @PostMapping
    public FinancialSummary createFinancialSummary(@RequestParam Double totalInvoicing, @RequestParam Double totalExpenses) {
        return financialSummaryService.addFinancialSummary(totalInvoicing, totalExpenses);
    }

    // Get all financial summaries
    @GetMapping("/loadFinancialSummary")
    public ResponseEntity<List<FinancialSummary>> getFinancialSummaryByDate(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        if (year != null && month != null) {
            return ResponseEntity.ok(financialSummaryService.getFinancialSummariesByMonthAndYear(year, month));
        }
        return ResponseEntity.ok(financialSummaryService.getAllFinancialSummaries());
    }

    // Update a financial summary
    @PutMapping("/{id}")
    public FinancialSummary updateFinancialSummary(@PathVariable Long id, @RequestParam Double totalInvoicing, @RequestParam Double totalExpenses) {
        return financialSummaryService.updateFinancialSummary(id, totalInvoicing, totalExpenses);
    }
}
