package com.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.dto.CarAvailableRequest;
import com.carrental.dto.CreateCarDTO;
import com.carrental.service.CarService;


@RestController
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Validated @RequestBody CreateCarDTO car) {
        System.out.println(car);
        return ResponseEntity.ok(carService.create(car));
    }
    @PostMapping("/{id}/check_availability")
    public ResponseEntity<String> checkBookingAvailability(@PathVariable Long id, @RequestBody CarAvailableRequest carAvailableRequest) {
        boolean isAvailable = carService.isCarAvailable(id, carAvailableRequest);
        if (isAvailable) {
            return ResponseEntity.ok("Car is available for booking.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Car is not available for booking.");
        }
    }

}
