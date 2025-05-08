package com.bezkoder.spring.jpa.postgresql.repository;

import com.bezkoder.spring.jpa.postgresql.model.MonthlySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MonthlySummaryRepository extends JpaRepository<MonthlySummary, Long> {

    Optional<MonthlySummary> findTopByOrderByMonthDesc(); // Get latest summary

    Optional<MonthlySummary> findByMonth(LocalDate month); // Get summary by month
}