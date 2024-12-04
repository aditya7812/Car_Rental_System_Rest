package com.carrental.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.carrental.dto.BookingRequest;
import com.carrental.exception.ResourceNotFoundException;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.User;
import com.carrental.repository.BookingRepository;
import com.carrental.repository.CarRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final CarRepository carRepo;

    public BookingService(BookingRepository bookingRepo, CarRepository carRepo) {
        this.bookingRepo = bookingRepo;
        this.carRepo = carRepo;
    }

    public Booking createBooking(BookingRequest bookingRequest) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Car car = carRepo.findById(bookingRequest.getCarId())
                    .orElseThrow(() -> new ResourceNotFoundException("Car with given id not found"));
        
        if (!"AVAILABLE".equalsIgnoreCase(car.getStatus())) {
            throw new IllegalStateException("Car is not available for booking");
        }

        List<Booking> existingBookings = bookingRepo.findByCarIdAndStatus(car.getId(), "Confirmed");
        for (Booking existingBooking : existingBookings) {
            if (!(bookingRequest.getEndDate().isBefore(existingBooking.getStartDate()) || 
                  bookingRequest.getStartDate().isAfter(existingBooking.getEndDate()))) {
                throw new IllegalStateException("Car is already booked for the requested period.");
            }
        }

        Booking booking = new Booking();
        booking.setCar(car);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setStatus("PENDING");
        booking.setUser(user);

        car.setStatus("BOOKED");
        carRepo.save(car);

        return bookingRepo.save(booking);
    }

}
