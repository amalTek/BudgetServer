package com.bezkoder.spring.jpa.postgresql.Service;

import com.bezkoder.spring.jpa.postgresql.model.MonthlySummary;
import com.bezkoder.spring.jpa.postgresql.repository.MonthlySummaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MonthlySummaryService {

    private final MonthlySummaryRepository repository;

    public MonthlySummaryService(MonthlySummaryRepository repository) {
        this.repository = repository;
    }

    public Optional<MonthlySummary> getLatestSummary() {
        return repository.findTopByOrderByMonthDesc();
    }

    public Optional<MonthlySummary> getSummaryByMonth(LocalDate month) {
        return repository.findByMonth(month);
    }
}