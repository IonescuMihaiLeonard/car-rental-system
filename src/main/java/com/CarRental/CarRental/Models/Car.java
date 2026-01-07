package com.CarRental.CarRental.Models;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String licensePlate;

    @Column
    private String vinNumber;



    @Column
    private String brand;
    @Column
    private String model;

    @Column
    private int year;

    @Column
    private String color;

    @Column
    private String chassis;

    @Column
    private String category;

    @Column
    private int seats;

    @Column
    private boolean isAutomatic;

    @Column
    private String fuelType;

    @Column
    private double rentalRatePerDay;

    @Column
    private int currentMileage;

    @Column
    private boolean currentlyRented;

    public long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    public String getVinNumber() {
        return vinNumber;
    }
    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }


    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }


    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }


    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }


    public String getChassis() {
        return chassis;
    }
    public void setChassis(String chassis) {
        this.chassis = chassis;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }


    public boolean isAutomatic() {
        return isAutomatic;
    }
    public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }


    public String getFuelType() {
        return fuelType;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }


    public double getRentalRatePerDay() {
        return rentalRatePerDay;
    }
    public void setRentalRatePerDay(double rentalRatePerDay) {
        this.rentalRatePerDay = rentalRatePerDay;
    }


    public int getCurrentMileage() {
        return currentMileage;
    }
    public void setCurrentMileage(int currentMileage) {
        this.currentMileage = currentMileage;
    }


    public boolean isCurrentlyRented() {
        return currentlyRented;
    }
    public void setCurrentlyRented(boolean currentlyRented) {
        this.currentlyRented = currentlyRented;
    }


    public void setId(long l) {
        this.id = l;
    }
}
