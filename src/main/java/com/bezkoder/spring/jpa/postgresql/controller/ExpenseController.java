package com.bezkoder.spring.jpa.postgresql.controller;

import com.bezkoder.spring.jpa.postgresql.model.Expense;
import com.bezkoder.spring.jpa.postgresql.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Get all expenses
    @GetMapping("/loadExpenseData")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Get one expense by ID
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    // Create a new expense
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    // Update an expense
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

        expense.setDate(expenseDetails.getDate());
        expense.setCategory(expenseDetails.getCategory());
        expense.setDescription(expenseDetails.getDescription());
        expense.setAmount(expenseDetails.getAmount());

        return expenseRepository.save(expense);
    }

    // Delete an expense
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
    }{
}}
