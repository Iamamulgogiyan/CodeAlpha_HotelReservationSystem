// ============================================================
//  HotelReservationSystem.java
//  Main class - CodeAlpha Task 3: Hotel Reservation System
//  Search, book and manage hotel rooms, with payment
//  simulation and file storage for bookings.
// ============================================================

import java.util.*;

public class HotelReservationSystem {

    private Hotel hotel;
    private Scanner scanner;
    private static final String SAVE_FILE = "bookings_data.txt";

    public HotelReservationSystem() {
        hotel = new Hotel("Hotel Sunrise");
        scanner = new Scanner(System.in);
    }

    // ---------- Safe input helpers (crash-proof, same style as Task 1 & 2) ----------
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number.");
            }
        }
    }

    private Room.RoomType readRoomType() {
        while (true) {
            System.out.print("Enter room type (STANDARD / DELUXE / SUITE): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Room.RoomType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Invalid type. Type STANDARD, DELUXE or SUITE.");
            }
        }
    }

    // ---------- Features ----------
    private void viewAllRooms() {
        System.out.println("\n=========== ALL ROOMS - " + hotel.getHotelName().toUpperCase() + " ===========");
        for (Room r : hotel.getAllRooms()) System.out.println(r);
        System.out.println("====================================================");
    }

    private void searchRooms() {
        Room.RoomType type = readRoomType();
        List<Room> available = hotel.searchAvailable(type);
        System.out.println("\n-------- Available " + type + " Rooms --------");
        if (available.isEmpty()) {
            System.out.println("Sorry, no " + type + " rooms are available right now.");
        } else {
            for (Room r : available) System.out.println(r);
        }
        System.out.println("------------------------------------------");
    }

    private void bookRoom() {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine().trim();
        if (guestName.isEmpty()) { System.out.println("⚠️ Guest name cannot be empty."); return; }
        int roomNumber = readInt("Enter room number to book: ");
        int nights = readInt("Enter number of nights: ");

        Booking booking = hotel.bookRoom(guestName, roomNumber, nights);
        if (booking == null) return;

        // ---- Payment simulation ----
        Room room = hotel.findRoom(roomNumber);
        System.out.println("\n----------- PAYMENT -----------");
        System.out.printf("Room %d (%s) x %d night(s) @ Rs. %.2f%n",
                roomNumber, room.getType(), nights, room.getPricePerNight());
        System.out.printf("Total Amount: Rs. %.2f%n", booking.getTotalAmount());
        System.out.println("Payment methods: 1. UPI   2. Card   3. Cash");
        int payChoice = readInt("Choose payment method (1-3): ");
        String method = (payChoice == 2) ? "Card" : (payChoice == 3) ? "Cash" : "UPI";
        System.out.println("💳 Processing " + method + " payment...");
        System.out.println("✅ Payment Successful!");
        System.out.println("--------------------------------");
        System.out.println("🎉 Booking Confirmed! Your Booking ID is: " + booking.getBookingId());
        System.out.println(booking);
    }

    private void cancelReservation() {
        System.out.print("Enter Booking ID to cancel (e.g. B1001): ");
        String id = scanner.nextLine().trim();
        hotel.cancelBooking(id);
    }

    private void viewBookingDetails() {
        System.out.print("Enter Booking ID (e.g. B1001): ");
        String id = scanner.nextLine().trim();
        Booking b = hotel.findBooking(id);
        if (b == null) {
            System.out.println("❌ No booking found with ID: " + id);
        } else {
            System.out.println("\n----------- BOOKING DETAILS -----------");
            System.out.println(b);
            System.out.println("---------------------------------------");
        }
    }

    private void viewAllBookings() {
        System.out.println("\n================ ALL BOOKINGS ================");
        if (hotel.getAllBookings().isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            for (Booking b : hotel.getAllBookings()) System.out.println(b);
        }
        System.out.println("==============================================");
    }

    public void start() {
        System.out.println("==============================================");
        System.out.println("       HOTEL RESERVATION SYSTEM");
        System.out.println("     CodeAlpha Task 3 - Amul Gogyan");
        System.out.println("==============================================");
        hotel.loadFromFile(SAVE_FILE);

        boolean running = true;
        while (running) {
            System.out.println("\n------------------ MENU ------------------");
            System.out.println("1. View All Rooms");
            System.out.println("2. Search Available Rooms by Type");
            System.out.println("3. Book a Room");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View Booking Details");
            System.out.println("6. View All Bookings");
            System.out.println("7. Save & Exit");
            System.out.println("------------------------------------------");
            int choice = readInt("Enter your choice (1-7): ");
            switch (choice) {
                case 1 -> viewAllRooms();
                case 2 -> searchRooms();
                case 3 -> bookRoom();
                case 4 -> cancelReservation();
                case 5 -> viewBookingDetails();
                case 6 -> viewAllBookings();
                case 7 -> { hotel.saveToFile(SAVE_FILE); running = false; System.out.println("👋 Thank you for using Hotel Reservation System!"); }
                default -> System.out.println("⚠️ Invalid choice. Please enter 1 to 7.");
            }
        }
    }

    public static void main(String[] args) {
        new HotelReservationSystem().start();
    }
}
