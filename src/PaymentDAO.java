package com.hotel.management;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public static boolean addPayment(int bookingId, BigDecimal amount, String method) {
        String sql = "INSERT INTO payments(booking_id, amount, method) VALUES (?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setBigDecimal(2, amount);
            ps.setString(3, method); // 'Cash','Card','UPI'
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static List<Payment> getPaymentsForBooking(int bookingId) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE booking_id=? ORDER BY payment_id";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Payment(
                            rs.getInt("payment_id"),
                            rs.getInt("booking_id"),
                            rs.getBigDecimal("amount"),
                            rs.getTimestamp("payment_date").toLocalDateTime(),
                            rs.getString("method")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
