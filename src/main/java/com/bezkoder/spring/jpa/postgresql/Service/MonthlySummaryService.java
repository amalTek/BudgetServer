package com.bezkoder.spring.jpa.postgresql.Service;

import com.bezkoder.spring.jpa.postgresql.DTO.MonthlyTransactionSummaryDTO;
import com.bezkoder.spring.jpa.postgresql.DTO.TransactionDTO;
import com.bezkoder.spring.jpa.postgresql.model.MonthlySummary;
import com.bezkoder.spring.jpa.postgresql.repository.MonthlySummaryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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


    public void processMonthlySummaries(List<MonthlyTransactionSummaryDTO> monthlySummaries) {
        for (MonthlyTransactionSummaryDTO monthlyData : monthlySummaries) {
            // Calculate totals
            BigDecimal totalExpenses = monthlyData.getTransactions().stream()
                    .filter(t -> "approved".equals(t.getStatus()))
                    .map(TransactionDTO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Convert month string to LocalDate (first day of month)
            LocalDate month = LocalDate.parse(monthlyData.getMonth() + "-01",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Find existing or create new summary
            MonthlySummary summary = repository.findByMonth(month)
                    .orElse(new MonthlySummary());

            // Update values
            summary.setMonth(month);
            summary.setTotalExpenses(summary.getTotalExpenses().add(totalExpenses));
            // For income, you might subtract (as per your requirement)
            summary.setTotalIncome(summary.getTotalIncome().subtract(totalExpenses));
            summary.setTotalInvoicing(summary.getTotalIncome().subtract(summary.getTotalExpenses()));

            // Save to database
            repository.save(summary);
        }
    }

}