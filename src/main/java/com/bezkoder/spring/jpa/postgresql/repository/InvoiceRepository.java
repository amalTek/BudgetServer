package com.bezkoder.spring.jpa.postgresql.repository;

import com.bezkoder.spring.jpa.postgresql.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByStatus(String status);

    @Query("SELECT i FROM Invoice i WHERE i.clientEmail = :email")
    List<Invoice> findByClientEmail(@Param("email") String email);

    @Query("SELECT i FROM Invoice i WHERE i.dueDate < CURRENT_DATE AND i.status <> 'PAID'")
    List<Invoice> findOverdueInvoices();

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.clientEmail = :email")
    BigDecimal calculateTotalAmountByClient(@Param("email") String email);
}