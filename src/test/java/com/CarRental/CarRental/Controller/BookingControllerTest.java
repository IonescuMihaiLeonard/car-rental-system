package com.CarRental.CarRental.Controller;

import com.CarRental.CarRental.Models.Booking;
import com.CarRental.CarRental.Models.Car;
import com.CarRental.CarRental.Models.User;
import com.CarRental.CarRental.Service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking1;
    private Booking booking2;
    private User user;
    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Smith");

        car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModel("Corolla");

        booking1 = new Booking();
        booking1.setId(1L);
        booking1.setUser(user);
        booking1.setCar(car);
        booking1.setStartDate(LocalDate.of(2025, 1, 1));
        booking1.setEndDate(LocalDate.of(2025, 1, 5));
        booking1.setStatus("ONGOING");
        booking1.setTotalCost(100);
        booking1.setMileage(0);

        booking2 = new Booking();
        booking2.setId(2L);
        booking2.setUser(user);
        booking2.setCar(car);
        booking2.setStartDate(LocalDate.of(2025, 2, 1));
        booking2.setEndDate(LocalDate.of(2025, 2, 5));
        booking2.setStatus("COMPLETED");
        booking2.setTotalCost(150);
        booking2.setMileage(200);
    }

    @Test
    void testGetAllBookings() {
        when(bookingService.getAllBookings()).thenReturn(List.of(booking1, booking2));

        List<Booking> result = bookingController.getAllBookings();
        assertEquals(2, result.size());
        assertEquals("ONGOING", result.get(0).getStatus());
        assertEquals("COMPLETED", result.get(1).getStatus());
    }

    @Test
    void testGetBookingByIdFound() {
        when(bookingService.getBookingById(1L)).thenReturn(Optional.of(booking1));

        ResponseEntity<Booking> response = bookingController.getBookingById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ONGOING", response.getBody().getStatus());
    }

    @Test
    void testGetBookingByIdNotFound() {
        when(bookingService.getBookingById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Booking> response = bookingController.getBookingById(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateBookingSuccess() throws Exception {
        when(bookingService.createBooking(eq(1L), eq(1L), any(Booking.class))).thenReturn(booking1);

        ResponseEntity<?> response = bookingController.createBooking(1L, 1L, booking1);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("ONGOING", ((Booking) response.getBody()).getStatus());
    }

    @Test
    void testCreateBookingFailure() throws Exception {
        when(bookingService.createBooking(eq(1L), eq(1L), any(Booking.class)))
                .thenThrow(new RuntimeException("Invalid booking"));

        ResponseEntity<?> response = bookingController.createBooking(1L, 1L, booking1);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid booking", response.getBody());
    }

    @Test
    void testUpdateBookingSuccess() throws Exception {
        Booking updatedBooking = new Booking();
        updatedBooking.setId(1L);
        updatedBooking.setStatus("COMPLETED");

        when(bookingService.updateBooking(eq(1L), any(Booking.class))).thenReturn(updatedBooking);

        ResponseEntity<?> response = bookingController.updateBooking(1L, updatedBooking);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("COMPLETED", ((Booking) response.getBody()).getStatus());
    }

    @Test
    void testUpdateBookingFailure() throws Exception {
        when(bookingService.updateBooking(eq(1L), any(Booking.class)))
                .thenThrow(new RuntimeException("Cannot update"));

        ResponseEntity<?> response = bookingController.updateBooking(1L, booking1);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Cannot update", response.getBody());
    }

    @Test
    void testCompleteBookingSuccess() {
        Booking completedBooking = new Booking();
        completedBooking.setId(1L);
        completedBooking.setStatus("COMPLETED");
        completedBooking.setMileage(100);

        when(bookingService.completeBooking(1L, 100)).thenReturn(completedBooking);

        ResponseEntity<Booking> response = bookingController.completeBooking(1L, 100);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("COMPLETED", response.getBody().getStatus());
        assertEquals(100, response.getBody().getMileage());
    }

    @Test
    void testCompleteBookingNotFound() {
        when(bookingService.completeBooking(99L, 100)).thenThrow(new RuntimeException());

        ResponseEntity<Booking> response = bookingController.completeBooking(99L, 100);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteBookingSuccess() {
        when(bookingService.deleteBooking(1L)).thenReturn(true);

        ResponseEntity<String> response = bookingController.deleteBooking(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Booking with id 1 deleted successfully.", response.getBody());
    }

    @Test
    void testDeleteBookingNotFound() {
        when(bookingService.deleteBooking(99L)).thenReturn(false);

        ResponseEntity<String> response = bookingController.deleteBooking(99L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Booking with id 99 not found.", response.getBody());
    }
}
