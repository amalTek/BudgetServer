package com.bezkoder.spring.jpa.postgresql.DTO;

public class MonthlyAmountRequest {
    private String month;
    private long totalAmount;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
