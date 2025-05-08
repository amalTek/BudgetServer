package com.bezkoder.spring.jpa.postgresql.repository;

import com.bezkoder.spring.jpa.postgresql.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // You can add custom queries here later if needed
}