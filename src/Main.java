package com.hotel.management;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== Hotel Management =====");
            System.out.println("1. Add Customer");
            System.out.println("2. List Customers");
            System.out.println("3. Add Room");
            System.out.println("4. List Rooms");
            System.out.println("5. List Available Rooms");
            System.out.println("6. Create Booking");
            System.out.println("7. Complete Booking");
            System.out.println("8. Add Payment");
            System.out.println("9. View Bookings (JOIN view)");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = safeInt();

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> listCustomers();
                case 3 -> addRoom();
                case 4 -> listRooms();
                case 5 -> listAvailableRooms();
                case 6 -> createBooking();
                case 7 -> completeBooking();
                case 8 -> addPayment();
                case 9 -> BookingDAO.printBookingsView();
                case 0 -> System.out.println("Bye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        sc.close();
    }

    // ---------- Menu actions ----------

    private static void addCustomer() {
        System.out.print("Name: ");   String name = sc.nextLine();
        System.out.print("Email: ");  String email = sc.nextLine();
        System.out.print("Phone: ");  String phone = sc.nextLine();
        System.out.print("Address: ");String address = sc.nextLine();
        boolean ok = CustomerDAO.addCustomer(new Customer(name, email, phone, address));
        System.out.println(ok ? "Customer added." : "Add failed.");
    }

    private static void listCustomers() {
        List<Customer> all = CustomerDAO.getAllCustomers();
        all.forEach(c -> System.out.println(c.getCustomerId()+" | "+c.getName()+" | "+c.getEmail()));
        if (all.isEmpty()) System.out.println("(none)");
    }

    private static void addRoom() {
        System.out.print("Room Number: "); String no = sc.nextLine();
        System.out.print("Type (Single/Double/Suite): "); String type = sc.nextLine();
        System.out.print("Price: "); BigDecimal price = new BigDecimal(sc.nextLine());
        boolean ok = RoomDAO.addRoom(new Room(no, type, price, "Available"));
        System.out.println(ok ? "Room added." : "Add failed.");
    }

    private static void listRooms() {
        var rooms = RoomDAO.getAllRooms();
        rooms.forEach(r -> System.out.println(r.getRoomId()+" | "+r.getRoomNumber()+" | "+r.getType()+" | "+r.getPrice()+" | "+r.getStatus()));
        if (rooms.isEmpty()) System.out.println("(none)");
    }

    private static void listAvailableRooms() {
        var rooms = RoomDAO.getAvailableRooms();
        rooms.forEach(r -> System.out.println(r.getRoomId()+" | "+r.getRoomNumber()+" | "+r.getType()+" | "+r.getPrice()+" | "+r.getStatus()));
        if (rooms.isEmpty()) System.out.println("(none)");
    }

    private static void createBooking() {
        System.out.print("Customer ID: "); int cid = safeInt();
        System.out.print("Room ID (Available): "); int rid = safeInt();
        System.out.print("Check-in (YYYY-MM-DD): "); LocalDate in = LocalDate.parse(sc.nextLine());
        System.out.print("Check-out (YYYY-MM-DD): "); LocalDate out = LocalDate.parse(sc.nextLine());

        int bookingId = BookingDAO.createBooking(new Booking(cid, rid, in, out, "Active"));
        System.out.println(bookingId > 0 ? "Booked with ID: " + bookingId : "Booking failed (room not available?).");
    }

    private static void completeBooking() {
        System.out.print("Booking ID to complete: "); int bid = safeInt();
        boolean ok = BookingDAO.completeBooking(bid);
        System.out.println(ok ? "Completed and room freed." : "Complete failed.");
    }

    private static void addPayment() {
        System.out.print("Booking ID: "); int bid = safeInt();
        System.out.print("Amount: "); BigDecimal amt = new BigDecimal(sc.nextLine());
        System.out.print("Method (Cash/Card/UPI): "); String method = sc.nextLine();
        boolean ok = PaymentDAO.addPayment(bid, amt, method);
        System.out.println(ok ? "Payment recorded." : "Payment failed.");
    }

    // ---------- helpers ----------
    private static int safeInt() {
        int v;
        while (true) {
            String s = sc.nextLine().trim();
            try { v = Integer.parseInt(s); break; }
            catch (NumberFormatException e) { System.out.print("Enter a valid number: "); }
        }
        return v;
    }
}
