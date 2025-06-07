package com.bezkoder.spring.jpa.postgresql.Service;

import com.bezkoder.spring.jpa.postgresql.model.FinancialSummary;
import com.bezkoder.spring.jpa.postgresql.repository.FinancialSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialSummaryService {

    @Autowired
    private FinancialSummaryRepository financialSummaryRepository;

    // Method to save a new financial summary
    public FinancialSummary addFinancialSummary(Double totalInvoicing, Double totalExpenses) {
        FinancialSummary financialSummary = new FinancialSummary(totalInvoicing, totalExpenses);
        return financialSummaryRepository.save(financialSummary);
    }

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

    // Method to update a financial summary (e.g., update invoicing or expenses)
    public FinancialSummary updateFinancialSummary(Long id, Double totalInvoicing, Double totalExpenses) {
        FinancialSummary existingSummary = financialSummaryRepository.findById(id).orElse(null);
        if (existingSummary != null) {
            existingSummary.setTotalInvoicing(totalInvoicing);
            existingSummary.setTotalExpenses(totalExpenses);
            existingSummary.setCurrentBalance(totalInvoicing - totalExpenses);
            return financialSummaryRepository.save(existingSummary);
        }
        return null; // Or throw an exception if not found
    }

    public FinancialSummary updateCurrentMonthExpenses(Double newExpenses) {
        // Get current month's summary or create new one if doesn't exist


        return null;
    }

}
