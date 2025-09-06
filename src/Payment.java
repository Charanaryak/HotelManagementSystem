package com.hotel.management;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int bookingId;
    private BigDecimal amount;
    private LocalDateTime paymentDate; // optional to use; DB sets default
    private String method; // Cash, Card, UPI

    public Payment(int bookingId, BigDecimal amount, String method) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
    }

    public Payment(int paymentId, int bookingId, BigDecimal amount, LocalDateTime paymentDate, String method) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
    }

    public int getPaymentId() { return paymentId; }
    public int getBookingId() { return bookingId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getMethod() { return method; }

    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public void setMethod(String method) { this.method = method; }
}
