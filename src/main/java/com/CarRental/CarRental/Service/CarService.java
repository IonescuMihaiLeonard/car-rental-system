package com.CarRental.CarRental.Service;

import com.CarRental.CarRental.Repo.CarRepo;
import com.CarRental.CarRental.Models.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepo carRepo;


    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepo.findById(id);
    }

    public List<Car> getCarsByBrand(String brand) {
        return carRepo.findAll()
            .stream()
            .filter(car -> car.getBrand().equalsIgnoreCase(brand))
            .toList();
    }

    public Car createCar(Car car) {
        return carRepo.save(car);
    }

    public List<Car> createCars(List<Car> cars) {
        return carRepo.saveAll(cars);
    }

    public Car updateCar(Long id, Car carDetails) {
        return carRepo.findById(id).map(car -> {
            car.setLicensePlate(carDetails.getLicensePlate());
            car.setVinNumber(carDetails.getVinNumber());
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setYear(carDetails.getYear());
            car.setColor(carDetails.getColor());
            car.setChassis(carDetails.getChassis());
            car.setCategory(carDetails.getCategory());
            car.setSeats(carDetails.getSeats());
            car.setAutomatic(carDetails.isAutomatic());
            car.setFuelType(carDetails.getFuelType());
            car.setRentalRatePerDay(carDetails.getRentalRatePerDay());
            car.setCurrentMileage(carDetails.getCurrentMileage());
            car.setCurrentlyRented(carDetails.isCurrentlyRented());
            return carRepo.save(car);
        }).orElse(null);
    }



    public boolean deleteCar(Long id) {
        return carRepo.findById(id).map(car -> {
            carRepo.delete(car);
            return true;
        }).orElse(false);
    }

}
