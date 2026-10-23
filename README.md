# CodeAlpha_HotelReservationSystem

A console-based **Hotel Reservation System** built in Core Java — CodeAlpha Java Programming Internship, **Task 4**.

Guests can search rooms by type (Standard, Deluxe, Suite), book a room with a payment simulation, cancel a reservation, and view booking details. All bookings are saved to a file, so they survive after the program closes.

## ✨ Features

- **11 rooms in 3 categories** — Standard (₹2,000/night), Deluxe (₹3,500/night), Suite (₹6,000/night)
- **View All Rooms** — with live availability status
- **Search by Type** — only available rooms of the chosen category
- **Book a Room** — auto Booking ID (B1001, B1002...), total = price × nights
- **Payment Simulation** — UPI / Card / Cash with confirmation and refund on cancellation
- **Cancel Reservation** — booking marked CANCELLED and the room becomes available again
- **Booking Details + All Bookings view**
- **File I/O Persistence** — bookings auto-save to `bookings_data.txt` and reload on next start, with room availability restored
- **Crash-proof input** — wrong input asks again instead of crashing

## 🛠️ Concepts Used

- **OOP** — 4 classes: `Room`, `Booking`, `Hotel`, `HotelReservationSystem` (encapsulation: private fields + getters)
- **ArrayList** — rooms and bookings storage
- **Enum** — `RoomType` (STANDARD / DELUXE / SUITE) and booking `Status` (CONFIRMED / CANCELLED)
- **File I/O** — `FileWriter` / `BufferedReader` for persistent bookings
- **Exception handling** — `try-catch` for safe numeric and enum input

## ▶️ How to Run

```bash
javac Room.java Booking.java Hotel.java HotelReservationSystem.java
java HotelReservationSystem
```

## 📸 Sample Output (verified demo run)

```
Room 201 (DELUXE) x 3 night(s) @ Rs. 3500.00
Total Amount: Rs. 10500.00
Payment methods: 1. UPI   2. Card   3. Cash
Processing UPI payment... Payment Successful!
Booking Confirmed! Your Booking ID is: B1001
Booking B1001 | Guest: Amul Gogyan | Room 201 (DELUXE) | 3 night(s) | Rs. 10500.00 | CONFIRMED

Cancel B1001 -> refund processed, Room 201 becomes Available again ✔
Bookings saved to bookings_data.txt and reloaded on next start ✔
```

## 📂 Files

| File | Purpose |
|---|---|
| `Room.java` | One room — number, type, price per night, availability |
| `Booking.java` | One reservation — guest, room, nights, total, status, file format |
| `Hotel.java` | Manages rooms + bookings — search, book, cancel, file save/load |
| `HotelReservationSystem.java` | Main class — menu and all user interaction |

## 🔮 Future Improvements

- Check-in / check-out dates instead of number of nights
- GUI version using Java Swing / JavaFX
- MySQL database instead of file storage

---
*Made by Amul Gogyan — Java Programming Intern @ CodeAlpha (Student ID: CA/DF1/310696)*
