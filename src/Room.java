package com.hotel.management;

import java.math.BigDecimal;

public class Room {
    private int roomId;
    private String roomNumber;
    private String type;  // Single, Double, Suite
    private BigDecimal price;
    private String status; // Available, Booked

    public Room(String roomNumber, String type, BigDecimal price, String status) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public Room(int roomId, String roomNumber, String type, BigDecimal price, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public int getRoomId() { return roomId; }
    public String getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public BigDecimal getPrice() { return price; }
    public String getStatus() { return status; }

    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setType(String type) { this.type = type; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
}
