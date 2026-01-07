package com.CarRental.CarRental.Controller;

import com.CarRental.CarRental.Models.Car;
import com.CarRental.CarRental.Service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;


    @GetMapping("/all")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Car>> getCarsByBrand(@PathVariable String brand) {
        List<Car> cars = carService.getCarsByBrand(brand);
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/create")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.createCar(car);
        return ResponseEntity.status(201).body(savedCar);
    }

    @PostMapping("/create-many")
    public ResponseEntity<List<Car>> createCars(@RequestBody List<Car> cars) {
        List<Car> savedCars = carService.createCars(cars);
        return ResponseEntity.status(201).body(savedCars);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Car updatedCar = carService.updateCar(id, carDetails);
        if (updatedCar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCar);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        boolean deleted = carService.deleteCar(id);
        if (deleted) {
            return ResponseEntity.ok("Car with id " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Car with id " + id + " not found.");
        }
    }

}
