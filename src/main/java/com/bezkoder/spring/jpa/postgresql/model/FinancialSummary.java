package com.bezkoder.spring.jpa.postgresql.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
@Table(name = "financial_summary")
@Entity
public class FinancialSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @Column(name = "total_invoicing")
    private Double totalInvoicing;

    @Column(name = "total_expenses") // Decimal(12, 2)
    private Double totalExpenses;

    @Column(name = "current_balance") // Decimal(12, 2)
    private Double currentBalance;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); // Timestamp field

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setTotalInvoicing(Double totalInvoicing) {
        this.totalInvoicing = totalInvoicing;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getTotalInvoicing() {
        return totalInvoicing;
    }

    public Long getId() {
        return id;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Default constructor (JPA requires it)
    public FinancialSummary() {}
}
