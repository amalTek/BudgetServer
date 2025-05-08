package com.bezkoder.spring.jpa.postgresql.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_summary")
public class MonthlySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate month;

    @Column(name = "total_expenses", nullable = false)
    private BigDecimal totalExpenses;

    @Column(name = "total_income", nullable = false)
    private BigDecimal totalIncome;

    @Column(name = "total_invoicing", nullable = false)
    private BigDecimal totalInvoicing;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalInvoicing() {
        return totalInvoicing;
    }

    public void setTotalInvoicing(BigDecimal totalInvoicing) {
        this.totalInvoicing = totalInvoicing;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // No setter for createdAt to keep it immutable
}