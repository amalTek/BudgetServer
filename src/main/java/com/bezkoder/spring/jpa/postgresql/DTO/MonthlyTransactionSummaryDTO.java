package com.bezkoder.spring.jpa.postgresql.DTO;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyTransactionSummaryDTO {
    private String month; // Format: "YYYY-MM"
    private List<TransactionDTO> transactions;

    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}

