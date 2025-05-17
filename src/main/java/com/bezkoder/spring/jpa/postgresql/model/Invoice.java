package com.bezkoder.spring.jpa.postgresql.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String corporate;
    private String address;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_address")
    private String clientAddress;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_terms")
    private String paymentTerms;

    private String currency;
    private String designation;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal amount;

    @Column(name = "vat_rate")
    private BigDecimal vatRate;

    @Column(name = "vat_amount")
    private BigDecimal vatAmount;

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public Long getId() {
        return id;
    }

    public String getCorporate() {
        return corporate;
    }

    public String getAddress() {
        return address;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDesignation() {
        return designation;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    private String status;

    // Getters and setters omitted for brevity
}
