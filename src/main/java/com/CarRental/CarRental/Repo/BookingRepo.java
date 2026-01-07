package com.CarRental.CarRental.Repo;

import com.CarRental.CarRental.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
