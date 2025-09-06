package com.hotel.management;

import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int customerId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status; // Active, Completed, Cancelled

    public Booking(int customerId, int roomId, LocalDate checkIn, LocalDate checkOut, String status) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Booking(int bookingId, int customerId, int roomId, LocalDate checkIn, LocalDate checkOut, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public int getRoomId() { return roomId; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public String getStatus() { return status; }

    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
    public void setStatus(String status) { this.status = status; }
}
