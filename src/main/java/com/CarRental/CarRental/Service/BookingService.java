package com.CarRental.CarRental.Service;

import com.CarRental.CarRental.Models.Car;
import com.CarRental.CarRental.Models.User;
import com.CarRental.CarRental.Models.Booking;

import com.CarRental.CarRental.Repo.BookingRepo;
import com.CarRental.CarRental.Repo.CarRepo;
import com.CarRental.CarRental.Repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }

    public Booking createBooking(Long userId, Long carId, Booking booking) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        LocalDate today = LocalDate.now();

        if (booking.getStartDate().isBefore(today)) {
            throw new RuntimeException("Booking cannot start in the past");
        }

        booking.setUser(user);
        booking.setCar(car);

        if (booking.getStartDate().isEqual(today)) {
            car.setCurrentlyRented(true);
            carRepo.save(car);
        }

        long days = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) + 1;
        booking.setTotalCost(days * car.getRentalRatePerDay());

        return bookingRepo.save(booking);
    }

    public Booking updateBooking(Long bookingId, Booking bookingDetails) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDate today = LocalDate.now();

        if (bookingDetails.getStartDate() != null) {
            if (bookingDetails.getStartDate().isBefore(today)) {
                throw new RuntimeException("Booking cannot start in the past");
            }
            booking.setStartDate(bookingDetails.getStartDate());
        }

        if (bookingDetails.getEndDate() != null) {
            booking.setEndDate(bookingDetails.getEndDate());
        }

        if (bookingDetails.getStatus() != null) {
            booking.setStatus(bookingDetails.getStatus());

            // Dacă status devine BOOKED și startDate este astăzi
            if (booking.getStatus().equals("BOOKED") && booking.getStartDate().isEqual(today)) {
                Car car = booking.getCar();
                car.setCurrentlyRented(true);
                carRepo.save(car);
            }
        }

        // Recalculăm totalCost dacă datele s-au schimbat
        long days = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) + 1;
        booking.setTotalCost(days * booking.getCar().getRentalRatePerDay());

        return bookingRepo.save(booking);
    }

    public Booking completeBooking(Long bookingId, int mileage) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDate today = LocalDate.now();

        if (booking.getStartDate().isAfter(today)) {
            booking.setStatus("CANCELED");
            booking.setEndDate(booking.getStartDate());
            booking.setTotalCost(booking.getCar().getRentalRatePerDay());
        } else {
            booking.setStatus("COMPLETED");
            booking.setMileage(mileage);

            Car car = booking.getCar();
            car.setCurrentMileage(car.getCurrentMileage() + mileage);
            if (car.isCurrentlyRented()) {
                car.setCurrentlyRented(false);
                carRepo.save(car);
            }
        }

        return updateBooking(bookingId, booking);
    }




    public boolean deleteBooking(Long id) {
        return bookingRepo.findById(id).map(booking -> {
            bookingRepo.delete(booking);
            return true;
        }).orElse(false);
    }
}
