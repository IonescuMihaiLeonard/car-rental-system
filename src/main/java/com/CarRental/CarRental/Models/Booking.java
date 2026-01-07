package com.CarRental.CarRental.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private double totalCost;

    @Column
    private int mileage;

    @Column(nullable = false)
    private String status;

    public Long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }


    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }



    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


    public int getMileage() {
        return mileage;
    }
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }


    public void setId(long l) {
        id = l;
    }
}
