package com.CarRental.CarRental.Controller;

import com.CarRental.CarRental.Models.Booking;
import com.CarRental.CarRental.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create/{userId}/{carId}")
    public ResponseEntity<?> createBooking(@PathVariable Long userId,
                                           @PathVariable Long carId,
                                           @RequestBody Booking bookingDetails) {
        try {
            Booking createdBooking = bookingService.createBooking(userId, carId, bookingDetails);
            return ResponseEntity.status(201).body(createdBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable Long bookingId,
                                           @RequestBody Booking bookingDetails) {
        try {
            Booking updatedBooking = bookingService.updateBooking(bookingId, bookingDetails);
            return ResponseEntity.ok(updatedBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Booking> completeBooking(@PathVariable Long id,
                                                   @RequestBody int mileage) {
        try {
            Booking completedBooking = bookingService.completeBooking(id, mileage);
            return ResponseEntity.ok(completedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (deleted) {
            return ResponseEntity.ok("Booking with id " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Booking with id " + id + " not found.");
        }
    }
}
