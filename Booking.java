// ============================================================
//  Booking.java
//  This class represents ONE reservation made by a guest.
// ============================================================

public class Booking {

    public enum Status { CONFIRMED, CANCELLED }

    private String bookingId;    // e.g. B1001
    private String guestName;
    private int roomNumber;
    private Room.RoomType roomType;
    private int nights;
    private double totalAmount;
    private Status status;

    public Booking(String bookingId, String guestName, int roomNumber,
                   Room.RoomType roomType, int nights, double totalAmount) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.status = Status.CONFIRMED;
    }

    public String getBookingId() { return bookingId; }
    public String getGuestName() { return guestName; }
    public int getRoomNumber() { return roomNumber; }
    public Room.RoomType getRoomType() { return roomType; }
    public int getNights() { return nights; }
    public double getTotalAmount() { return totalAmount; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    // One line for file saving: B1001,Amul Gogyan,201,DELUXE,3,10500.00,CONFIRMED
    public String toFileString() {
        return bookingId + "," + guestName + "," + roomNumber + "," + roomType + "," +
               nights + "," + String.format("%.2f", totalAmount) + "," + status;
    }

    public static Booking fromFileString(String line) {
        String[] p = line.split(",");
        Booking b = new Booking(p[0], p[1], Integer.parseInt(p[2]),
                Room.RoomType.valueOf(p[3]), Integer.parseInt(p[4]),
                Double.parseDouble(p[5]));
        b.status = Status.valueOf(p[6]);
        return b;
    }

    @Override
    public String toString() {
        return String.format("Booking %s | Guest: %-16s | Room %d (%s) | %d night(s) | Rs. %.2f | %s",
                bookingId, guestName, roomNumber, roomType, nights, totalAmount, status);
    }
}
