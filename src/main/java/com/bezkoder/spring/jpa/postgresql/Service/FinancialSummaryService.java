package com.bezkoder.spring.jpa.postgresql.Service;

import com.bezkoder.spring.jpa.postgresql.model.FinancialSummary;
import com.bezkoder.spring.jpa.postgresql.repository.FinancialSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class FinancialSummaryService {

    @Autowired
    private FinancialSummaryRepository financialSummaryRepository;


    public List<FinancialSummary> getAllFinancialSummaries() {
        return financialSummaryRepository.findAll();
    }

    // Method to get summaries by month and year
    public List<FinancialSummary> getFinancialSummariesByMonthAndYear(int year, int month) {
        return financialSummaryRepository.findByYearAndMonth(year, month);
    }

    // Combined method that handles both cases
    public List<FinancialSummary> getFinancialSummaries(Integer year, Integer month) {
        if (year != null && month != null) {
            return getFinancialSummariesByMonthAndYear(year, month);
        }
        return getAllFinancialSummaries();
    }
    public void updateFinancialSummary(String monthYear, long amount) {
        YearMonth targetMonth = YearMonth.parse(monthYear);

        LocalDateTime startOfMonth = targetMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = targetMonth.atEndOfMonth().atTime(23, 59, 59);

        List<FinancialSummary> summaries = financialSummaryRepository.findByCreatedAtBetween(startOfMonth, endOfMonth);

        if (!summaries.isEmpty()) {
            FinancialSummary existing = summaries.get(0);
            double newExpenses = existing.getTotalExpenses() + amount;
            existing.setTotalExpenses(newExpenses);
            existing.setCurrentBalance(existing.getCurrentBalance() - amount);

            financialSummaryRepository.save(existing);
        } else {
            FinancialSummary newSummary = new FinancialSummary();
            newSummary.setCreatedAt(startOfMonth);
            newSummary.setTotalExpenses((double) amount);
            newSummary.setTotalInvoicing(0.0); // Set properly if known
            newSummary.setCurrentBalance(0.0 - amount); // Or any logic you want

            financialSummaryRepository.save(newSummary);
        }
    }




}
