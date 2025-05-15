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

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalInvoicing() {
        return totalInvoicing;
    }

    public void setTotalInvoicing(Double totalInvoicing) {
        this.totalInvoicing = totalInvoicing;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Constructor for creating a new record
    public FinancialSummary(Double totalInvoicing, Double totalExpenses) {
        this.totalInvoicing = totalInvoicing;
        this.totalExpenses = totalExpenses;
        this.currentBalance = totalInvoicing - totalExpenses;
    }

    // Default constructor (JPA requires it)
    public FinancialSummary() {}
}
