package com.bezkoder.spring.jpa.postgresql.controller;

import com.bezkoder.spring.jpa.postgresql.DTO.MonthlyAmountRequest;
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

    @PostMapping("/updateFinancialSummary")
    public ResponseEntity<Void> updateFinancialSummary(@RequestBody List<MonthlyAmountRequest> requests) {
        for (MonthlyAmountRequest request : requests) {
            financialSummaryService.updateFinancialSummary(request.getMonth(), request.getTotalAmount());
        }
        return ResponseEntity.ok().build();
    }

}