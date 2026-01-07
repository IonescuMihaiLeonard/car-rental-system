package com.CarRental.CarRental.Repo;

import com.CarRental.CarRental.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, Long> {
}
