package com.carrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.dto.CarAvailableRequest;
import com.carrental.dto.CreateCarDTO;
import com.carrental.exception.ResourceNotFoundException;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.repository.BookingRepository;
import com.carrental.repository.CarRepository;


@Service
public class CarService {
    public final CarRepository carRepo;
    public final BookingRepository bookingRepo;

    public CarService(CarRepository carRepo, BookingRepository bookingRepo) {
        this.carRepo = carRepo;
        this.bookingRepo = bookingRepo;
    }

    public String create(CreateCarDTO carReq) {
        Car car = new Car();
        car.setCarModel(carReq.getCarModel());
        car.setCompany(carReq.getCompany());
        car.setLicencePlate(carReq.getLicencePlate());
        car.setPricePerDay(carReq.getPricePerDay());
        car.setStatus(carReq.getStatus());
        carRepo.save(car);
        return "Create Successfully";
    }

    public boolean isCarAvailable(Long id, CarAvailableRequest carAvailableRequest) {
        Car car = carRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Car not found for given Id :" + id));
    
        List<Booking> existingBookings = bookingRepo.findByCarIdAndStatus(car.getId(), "Confirmed");
        for (Booking existingBooking : existingBookings) {
            if (!(carAvailableRequest.getEndDate().isBefore(existingBooking.getStartDate()) || 
                    carAvailableRequest.getStartDate().isAfter(existingBooking.getEndDate()))) {
                return false;
            }
        }

        return true;
    }




}
