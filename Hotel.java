// ============================================================
//  Hotel.java
//  This class manages all rooms and all bookings.
//  It handles searching, booking, cancelling and file saving.
// ============================================================

import java.io.*;
import java.util.*;

public class Hotel {

    private String hotelName;
    private List<Room> rooms;
    private List<Booking> bookings;
    private int nextBookingNumber; // booking IDs: B1001, B1002, ...

    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.nextBookingNumber = 1001;
        addRooms();
    }

    private void addRooms() {
        // Standard rooms - Rs. 2,000 per night
        rooms.add(new Room(101, Room.RoomType.STANDARD, 2000));
        rooms.add(new Room(102, Room.RoomType.STANDARD, 2000));
        rooms.add(new Room(103, Room.RoomType.STANDARD, 2000));
        rooms.add(new Room(104, Room.RoomType.STANDARD, 2000));
        // Deluxe rooms - Rs. 3,500 per night
        rooms.add(new Room(201, Room.RoomType.DELUXE, 3500));
        rooms.add(new Room(202, Room.RoomType.DELUXE, 3500));
        rooms.add(new Room(203, Room.RoomType.DELUXE, 3500));
        rooms.add(new Room(204, Room.RoomType.DELUXE, 3500));
        // Suite rooms - Rs. 6,000 per night
        rooms.add(new Room(301, Room.RoomType.SUITE, 6000));
        rooms.add(new Room(302, Room.RoomType.SUITE, 6000));
        rooms.add(new Room(303, Room.RoomType.SUITE, 6000));
    }

    public String getHotelName() { return hotelName; }
    public List<Room> getAllRooms() { return rooms; }
    public List<Booking> getAllBookings() { return bookings; }

    public Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }

    // Search: only available rooms of the given type
    public List<Room> searchAvailable(Room.RoomType type) {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            if (r.getType() == type && r.isAvailable()) result.add(r);
        }
        return result;
    }

    // ---------------- BOOK A ROOM ----------------
    public Booking bookRoom(String guestName, int roomNumber, int nights) {
        Room room = findRoom(roomNumber);
        if (room == null) {
            System.out.println("❌ Room " + roomNumber + " does not exist.");
            return null;
        }
        if (!room.isAvailable()) {
            System.out.println("❌ Room " + roomNumber + " is already booked. Please choose another room.");
            return null;
        }
        if (nights <= 0) {
            System.out.println("❌ Number of nights must be at least 1.");
            return null;
        }
        double total = room.getPricePerNight() * nights;
        String bookingId = "B" + (nextBookingNumber++);
        Booking booking = new Booking(bookingId, guestName, roomNumber, room.getType(), nights, total);
        bookings.add(booking);
        room.setAvailable(false);
        return booking;
    }

    // ---------------- CANCEL A RESERVATION ----------------
    public boolean cancelBooking(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) {
                if (b.getStatus() == Booking.Status.CANCELLED) {
                    System.out.println("⚠️ This booking is already cancelled.");
                    return false;
                }
                b.setStatus(Booking.Status.CANCELLED);
                Room room = findRoom(b.getRoomNumber());
                if (room != null) room.setAvailable(true); // room becomes free again
                System.out.printf("✅ Booking %s cancelled. Refund of Rs. %.2f processed (payment simulation).%n",
                        b.getBookingId(), b.getTotalAmount());
                return true;
            }
        }
        System.out.println("❌ Booking ID not found: " + bookingId);
        return false;
    }

    public Booking findBooking(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) return b;
        }
        return null;
    }

    // ---------------- FILE I/O ----------------
    public void saveToFile(String fileName) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("NEXT," + nextBookingNumber);
            for (Booking b : bookings) pw.println(b.toFileString());
            System.out.println("💾 Bookings saved to " + fileName);
        } catch (IOException e) {
            System.out.println("⚠️ Could not save bookings: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("NEXT,")) {
                    nextBookingNumber = Integer.parseInt(line.split(",")[1]);
                } else if (!line.isBlank()) {
                    Booking b = Booking.fromFileString(line);
                    bookings.add(b);
                    // a CONFIRMED booking means its room is not available
                    if (b.getStatus() == Booking.Status.CONFIRMED) {
                        Room room = findRoom(b.getRoomNumber());
                        if (room != null) room.setAvailable(false);
                    }
                }
            }
            if (!bookings.isEmpty())
                System.out.println("📂 Loaded " + bookings.size() + " saved booking(s) from file.");
        } catch (IOException e) {
            System.out.println("⚠️ Could not load saved bookings, starting fresh.");
        }
    }
}
