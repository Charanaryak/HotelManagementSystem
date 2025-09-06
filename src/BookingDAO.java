package com.hotel.management;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static int createBooking(Booking b) {
        String checkRoom = "SELECT status FROM rooms WHERE room_id=?";
        String insert = "INSERT INTO bookings(customer_id, room_id, check_in, check_out, status) VALUES (?,?,?,?,?)";
        String bookRoom = "UPDATE rooms SET status='Booked' WHERE room_id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);

            // 1) room must be Available
            try (PreparedStatement ps = c.prepareStatement(checkRoom)) {
                ps.setInt(1, b.getRoomId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next() || !"Available".equals(rs.getString("status"))) {
                        c.rollback();
                        return -1; // not available
                    }
                }
            }

            // 2) insert booking and capture generated key
            int newId = -1;
            try (PreparedStatement ps = c.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, b.getCustomerId());
                ps.setInt(2, b.getRoomId());
                ps.setDate(3, java.sql.Date.valueOf(b.getCheckIn()));
                ps.setDate(4, java.sql.Date.valueOf(b.getCheckOut()));
                ps.setString(5, b.getStatus()); // "Active"
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) newId = keys.getInt(1);
                }
            }

            // 3) mark room Booked
            try (PreparedStatement ps = c.prepareStatement(bookRoom)) {
                ps.setInt(1, b.getRoomId());
                ps.executeUpdate();
            }

            c.commit();
            return newId; // ✅ return booking_id
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    /** Mark booking Completed and free the room. */
    public static boolean completeBooking(int bookingId) {
        String get = "SELECT room_id FROM bookings WHERE booking_id=?";
        String updBooking = "UPDATE bookings SET status='Completed' WHERE booking_id=?";
        String freeRoom = "UPDATE rooms SET status='Available' WHERE room_id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);

            int roomId;
            try (PreparedStatement ps = c.prepareStatement(get)) {
                ps.setInt(1, bookingId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) { c.rollback(); return false; }
                    roomId = rs.getInt("room_id");
                }
            }

            try (PreparedStatement ps = c.prepareStatement(updBooking)) {
                ps.setInt(1, bookingId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(freeRoom)) {
                ps.setInt(1, roomId);
                ps.executeUpdate();
            }

            c.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Mark booking Cancelled and free the room. */
    public static boolean cancelBooking(int bookingId) {
        String get = "SELECT room_id FROM bookings WHERE booking_id=?";
        String updBooking = "UPDATE bookings SET status='Cancelled' WHERE booking_id=?";
        String freeRoom = "UPDATE rooms SET status='Available' WHERE room_id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);

            int roomId;
            try (PreparedStatement ps = c.prepareStatement(get)) {
                ps.setInt(1, bookingId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) { c.rollback(); return false; }
                    roomId = rs.getInt("room_id");
                }
            }

            try (PreparedStatement ps = c.prepareStatement(updBooking)) {
                ps.setInt(1, bookingId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(freeRoom)) {
                ps.setInt(1, roomId);
                ps.executeUpdate();
            }

            c.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Basic list (raw table). */
    public static List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY booking_id DESC";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** Pretty view with JOINs (great to demo in interviews). */
    public static void printBookingsView() {
        String sql = """
            SELECT b.booking_id, c.name AS customer, r.room_number, r.type,
                   b.check_in, b.check_out, b.status
            FROM bookings b
            JOIN customers c ON b.customer_id = c.customer_id
            JOIN rooms r     ON b.room_id = r.room_id
            ORDER BY b.booking_id DESC
            """;
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("#%d | %-18s | Room %s (%s) | %s → %s | %s%n",
                        rs.getInt("booking_id"),
                        rs.getString("customer"),
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getDate("check_in"),
                        rs.getDate("check_out"),
                        rs.getString("status"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
