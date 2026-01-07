package com.CarRental.CarRental.Controller;

import com.CarRental.CarRental.Models.Car;
import com.CarRental.CarRental.Service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService; // <-- aici Spring va injecta un mock

    private ObjectMapper objectMapper = new ObjectMapper();

    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        car1 = new Car();
        car1.setId(1L);
        car1.setBrand("Audi");
        car1.setModel("A4");

        car2 = new Car();
        car2.setId(2L);
        car2.setBrand("BMW");
        car2.setModel("X5");
    }

    @Test
    void testGetAllCars() throws Exception {
        when(carService.getAllCars()).thenReturn(List.of(car1, car2));

        mockMvc.perform(get("/cars/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].brand").value("Audi"))
                .andExpect(jsonPath("$[1].brand").value("BMW"));
    }

    @Test
    void testCreateCars() throws Exception {
        List<Car> carsToCreate = List.of(car1, car2);
        when(carService.createCars(any())).thenReturn(carsToCreate);

        mockMvc.perform(post("/cars/create-many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carsToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].brand").value("Audi"))
                .andExpect(jsonPath("$[1].brand").value("BMW"));
    }

    @Test
    void testUpdateCarSuccess() throws Exception {
        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setBrand("AudiUpdated");
        updatedCar.setModel("A4Updated");

        when(carService.updateCar(any(Long.class), any(Car.class))).thenReturn(updatedCar);

        mockMvc.perform(put("/cars/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("AudiUpdated"))
                .andExpect(jsonPath("$.model").value("A4Updated"));
    }

    @Test
    void testUpdateCarNotFound() throws Exception {
        when(carService.updateCar(any(Long.class), any(Car.class))).thenReturn(null);

        mockMvc.perform(put("/cars/update/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCarByIdFound() throws Exception {
        when(carService.getCarById(1L)).thenReturn(Optional.of(car1));

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Audi"));
    }

    @Test
    void testGetCarByIdNotFound() throws Exception {
        when(carService.getCarById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cars/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCarsByBrand() throws Exception {
        when(carService.getCarsByBrand("Audi")).thenReturn(List.of(car1));

        mockMvc.perform(get("/cars/brand/Audi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Audi"));
    }

    @Test
    void testCreateCar() throws Exception {
        when(carService.createCar(any(Car.class))).thenReturn(car1);

        mockMvc.perform(post("/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Audi"));
    }

    @Test
    void testDeleteCarSuccess() throws Exception {
        when(carService.deleteCar(1L)).thenReturn(true);

        mockMvc.perform(delete("/cars/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car with id 1 deleted successfully."));
    }

    @Test
    void testDeleteCarNotFound() throws Exception {
        when(carService.deleteCar(99L)).thenReturn(false);

        mockMvc.perform(delete("/cars/delete/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Car with id 99 not found."));
    }
}
