package com.bezkoder.spring.jpa.postgresql.controller;


import com.bezkoder.spring.jpa.postgresql.Service.MonthlySummaryService;
import com.bezkoder.spring.jpa.postgresql.model.MonthlySummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/summary")
@CrossOrigin(origins = "*") // Enable CORS for frontend
public class MonthlySummaryController {

    private final MonthlySummaryService service;

    public MonthlySummaryController(MonthlySummaryService service) {
        this.service = service;
    }

    @GetMapping("/latest")
    public ResponseEntity<MonthlySummary> getLatestSummary() {
        return service.getLatestSummary()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{month}")
    public ResponseEntity<MonthlySummary> getByMonth(@PathVariable String month) {
        try {
            LocalDate parsedMonth = LocalDate.parse(month);
            return service.getSummaryByMonth(parsedMonth)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
