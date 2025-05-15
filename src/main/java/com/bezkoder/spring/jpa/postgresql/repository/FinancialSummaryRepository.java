package com.bezkoder.spring.jpa.postgresql.repository;


import com.bezkoder.spring.jpa.postgresql.model.FinancialSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialSummaryRepository extends JpaRepository<FinancialSummary, Long> {
    // Custom query methods can be added here if needed
    @Query("SELECT f FROM FinancialSummary f WHERE YEAR(f.createdAt) = :year AND MONTH(f.createdAt) = :month")
    List<FinancialSummary> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}