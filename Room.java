// ============================================================
//  Room.java
//  This class represents ONE hotel room.
//  Rooms are of 3 types: STANDARD, DELUXE and SUITE,
//  each with a different price per night.
// ============================================================

public class Room {

    public enum RoomType { STANDARD, DELUXE, SUITE }

    private int roomNumber;
    private RoomType type;
    private double pricePerNight;
    private boolean available;

    public Room(int roomNumber, RoomType type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.available = true; // every room starts free
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("Room %-4d | %-9s | Rs. %7.2f/night | %s",
                roomNumber, type, pricePerNight, available ? "Available" : "Booked");
    }
}
